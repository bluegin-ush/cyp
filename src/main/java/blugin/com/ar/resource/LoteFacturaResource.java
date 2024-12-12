package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.EstadoLote;
import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.LoteFactura;
import blugin.com.ar.cyp.model.Socio;
import blugin.com.ar.dto.LoteFacturaDTO;
import blugin.com.ar.repository.LoteFacturaRepository;
import blugin.com.ar.repository.SocioRepository;
import blugin.com.ar.service.FacturaService;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ResourceProperties(paged = false)
@Path("/factura-lote")
@Transactional
public class LoteFacturaResource {

    @Inject
    SocioRepository socioRepository;

    @Inject
    FacturaService facturaService;

    @Inject
    LoteFacturaRepository loteFacturaRepository;

    @DELETE
    @Path("/{loteId}")
    @Transactional
    public Response eliminarLote(@PathParam("loteId") Long loteId) {
        // Buscar el lote por su ID
        LoteFactura loteFactura = LoteFactura.findById(loteId);

        if (loteFactura == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("El lote de facturas no fue encontrado").build();
        }

        // Verificar si el lote tiene facturas asociadas
        //if (!loteFactura.estado.equals(EstadoLote.EN_CONSTRUCCION)) {

        //TODO, borrar facturas pre-emitidas????
        //Si hay alguna factura emitida ya no podemos eliminar el lote.
        if (loteFactura.idFacturasEmitidas.size()>0) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("No se puede eliminar el lote, tiene facturas emitidas").build();
        }

        //eliminamos el lote (y en casacada sus facturas pre-emitidas)
        loteFactura.delete();

        return Response.ok("Lote eliminado con éxito").build();
    }

    @GET
    @Path("/existe-en-mes-actual")
    public Response existeLoteMesActual() {
        boolean existe = loteFacturaRepository.existeLoteEnMesActual();

        return Response.ok().entity(existe).build();
    }

    @GET
    @Path("/en-mes-actual")
    public Response lotesEnMesActual() {
        List<LoteFactura> lotes = loteFacturaRepository.lotesDelMesActual();

        if (lotes.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("No existen lotes").build();
        } else {
            return Response.ok().entity(lotes).build();
        }
    }

    @POST
    @Path("/crear")
    @Transactional
    public Response generarLote(List<Long> sociosIds) {



        LoteFactura lote = new LoteFactura();
            lote.fechaGeneracion = LocalDateTime.now();
            lote.progreso = 0;
            lote.estado = EstadoLote.EN_CONSTRUCCION;

            List<Socio> sociosActivos = (sociosIds == null || sociosIds.isEmpty())
                    //Si no me pasan socios, obtengo todos
                    //? socioRepository.todosConEntidadYServicio()
                    //tengo que procesar todos los socios que tienen servicios asociado
                    ? socioRepository.todosConServicio()

                    //uso los socios que me pasaron
                    : socioRepository.todosLosSociosValidados(sociosIds);


            //loteFacturaRepository.persist(lote);

            // Inicia el proceso
            facturaService.preFacturar(lote, sociosActivos);

            for(Factura f: lote.facturas){
                log.info("FacturaId: "+f.id);
            }

        return Response.accepted(lote).build();

    }

    private static final Logger log = LoggerFactory.getLogger(LoteFacturaResource.class);

    @POST
    @Path("/facturar/{loteId}")
    //@Timeout(value = 30, unit = TimeUnit.SECONDS)
    //@Fallback(fallbackMethod = "operacionFallback")
    //@Transactional
    public Response facturarLote(@PathParam("loteId") Long id) throws InterruptedException {

        LoteFactura lote = LoteFactura.findById(id);

        if(lote == null){

            return Response.status(Response.Status.NOT_FOUND).entity("El lote no se encuentra").build();

        } else {

            if (lote.estado.equals(EstadoLote.EN_PROCESO)) {

                log.info("Cantidad de facturas a procesar: "+lote.facturas.size());
                //
                int tamano = 10;
                int paginas = lote.facturas.size() / tamano;
                int resto =  lote.facturas.size() % tamano;

                log.info("Páginas a procesar: " + paginas);
                log.info("Resto de facturas a procesar: " + resto);

                //pagino la facturación
                for (int pagina=0; pagina < paginas; pagina++) {

                    int desde = pagina * tamano;
                    int hasta = (desde + tamano);

                    log.info("procesamos pagina: "+pagina+" - desde:" + desde + " - hasta: "+hasta);
                    facturaService.facturarEnLote(lote.id, desde, hasta);
                    //log.info("ESPERAMOS 5 seg...");
                    //Thread.sleep(5000);

                }

                //facturo el resto que me quedó
                int desde = paginas * tamano;
                int hasta = desde + resto ;
                log.info("procesamos el resto, desde:" + desde + " - hasta: "+ hasta);
                facturaService.facturarEnLote(lote.id, desde, hasta);

                //
                return Response.accepted(lote).build();

            } else if (lote.estado.equals(EstadoLote.COMPLETADO) ) {

                //
                return Response.status(Response.Status.OK).entity("El lote se procesó").entity(lote).build();

            } else {

                //
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("El lote se encuentra en un estado no esperado").build();

            }

        }

    }

    @GET
    public Response getLotes(@QueryParam("desde") LocalDate desde,
                             @QueryParam("hasta") LocalDate hasta) {
        if (desde == null) {
            desde = LocalDate.now();
        }

        if (hasta == null) {
            hasta = LocalDate.now();
        }

        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.atTime(LocalTime.MAX);

        //
        List<LoteFactura> lotes = LoteFactura.find("fechaGeneracion >= ?1 and fechaGeneracion <= ?2", inicio, fin).list();

        if(lotes == null || lotes.isEmpty()) {
            return Response.noContent().build();
        } else {
            List<LoteFacturaDTO> lotesDTO = lotes.stream()
                    .map(LoteFacturaDTO::new)
                    .collect(Collectors.toList());
            return Response.ok(lotesDTO).build();
        }

    }

    @GET
    @Path("/{loteId}")
    public Response getLote(@PathParam("loteId") Long id){

        LoteFactura lote = LoteFactura.findById(id);

        if(lote == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }else{
            return Response.ok(lote).build();
        }

    }
}

package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.EstadoLote;
import blugin.com.ar.cyp.model.LoteFactura;
import blugin.com.ar.cyp.model.Socio;
import blugin.com.ar.repository.LoteFacturaRepository;
import blugin.com.ar.repository.SocioRepository;
import blugin.com.ar.service.FacturaService;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@ResourceProperties(paged = false)
@Path("/factura-lote")
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
        if (loteFactura.estado.equals(EstadoLote.COMPLETADO)||loteFactura.estado.equals(EstadoLote.FALLIDO)) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("No se puede eliminar el lote, tiene facturas creadas y asociadas").build();
        }

        // Si no tiene facturas asociadas, proceder con la eliminación
        loteFactura.delete();
        return Response.ok("Lote eliminado con éxito").build();
    }

    @POST
    @Path("/crear/{mes}/{anio}")
    @Transactional
    public Response generarLote(List<Long> sociosIds,
                                @PathParam("mes") int mes,
                                @PathParam("anio") int anio) {


        LoteFactura lote = LoteFactura.find("mes = ?1 and anio = ?2", mes, anio).firstResult();

        if (lote == null) {
            lote = new LoteFactura(mes, anio);
            lote.mes = mes;
            lote.anio = anio;
            lote.fechaGeneracion = LocalDateTime.now();
            lote.progreso = 0;
            lote.estado = EstadoLote.EN_CONSTRUCCION;

            List<Socio> sociosActivos = (sociosIds == null || sociosIds.isEmpty())
                    //Si no me pasan socios, obtengo todos
                    //? socioRepository.todosConEntidadYServicio()
                    //tengo que procesar todos los socios que tienen servicios asociado
                    ? socioRepository.todosConServicio()

                    //uso los socios que me pasaron
                    : Socio.list("id in ?1 and activo = true", sociosIds);

            loteFacturaRepository.persist(lote);

            // Inicia el proceso asincrónico
            facturaService.preFacturar(lote, sociosActivos);

        }

        return Response.accepted(lote).build();

    }

    @POST
    @Path("/facturar/{loteId}")
    @Transactional
    public Response facturarLote(@PathParam("loteId") Long id) {

        LoteFactura lote = LoteFactura.findById(id);

        if(lote == null){

            return Response.status(Response.Status.NOT_FOUND).entity("El lote no se encuentra").build();

        } else {

            if (lote.estado.equals(EstadoLote.EN_PROCESO)) {

                //
                facturaService.facturarEnLote(lote);

                //
                return Response.accepted(lote).build();

            } else if (lote.estado.equals(EstadoLote.FALLIDO) || (lote.estado.equals(EstadoLote.COMPLETADO)) ) {

                //
                return Response.status(Response.Status.OK).entity("El lote se procesó").entity(lote).build();

            } else {

                //
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("El lote se encuentra en un estado no esperado").build();

            }

        }

    }

    @GET
    public Response getLotes(){

        List<LoteFactura> lotes = LoteFactura.findAll().list();

        if(lotes == null) {
            return Response.noContent().build();
        }else{
            return Response.ok(lotes).build();
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

package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.dto.FacturaDTO;
import blugin.com.ar.dto.FacturaMapper;
import blugin.com.ar.dto.NotaDeCreditoDTO;
import blugin.com.ar.repository.ConfiguracionRepository;
import blugin.com.ar.repository.FacturaRepository;
import blugin.com.ar.repository.LoteFacturaRepository;
import blugin.com.ar.repository.SocioRepository;
import blugin.com.ar.service.FacturaService;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.reactivestreams.Publisher;
import io.smallrye.mutiny.Multi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/factura")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class Facturar {

    private static final Logger log = LoggerFactory.getLogger(Facturar.class);
    @Inject
    FacturaRepository facturaRepository;

    @Inject
    SocioRepository socioRepository;

    @Inject
    FacturaService facturaService;

    @Inject
    ConfiguracionRepository configuracionRepository;

    @GET
    public Response obtenerFacturas(@QueryParam("desde") @DefaultValue("2024-01-01")LocalDate desde ,
                                    @QueryParam("hasta") @DefaultValue("2100-12-31")LocalDate hasta){

        System.out.println(desde);
        System.out.println(hasta);

        return Response.ok(facturaRepository.buscarFacturasEntreFechas(desde, hasta)).build();
    }

    @GET
    @Path("/count")
    public Response obtenerCantidadDeFacturas(){

        return Response.ok(facturaRepository.count()).build();

    }

    @GET
    @Path("/{facturaId}")
    public Response obtenerUnaFactura(@PathParam("facturaId") Long facturaId) {

        Factura factura = facturaRepository.findById(facturaId);

        // Verificamos si el socio existe
        if (factura == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("La factura no existe").build();
        }

        return Response.status(Response.Status.OK).entity(factura).build();
    }

    @DELETE
    @Path("/{facturaId}")
    public Response cancelar(@PathParam("facturaId") Long facturaId ,NotaDeCreditoDTO notaDTO) {

        Factura factura = facturaRepository.findById(facturaId);

        // Verificamos si el socio existe
        if (factura == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("La factura no existe").build();
        }
         else if (factura.estado.equals(EstadoFactura.CANCELADA)) {
            return Response.status(Response.Status.CONFLICT).entity("La factura ya se ha cancelado").build();
        }

        try {

            //factura.estado = EstadoFactura.CANCELADA;
            try{
                factura.estado = asignarEstadoSegunTotales(factura, notaDTO);
            }catch (Exception e){
                return Response.status(Response.Status.CONFLICT).entity("La nota de crédito excede el total de la factura").build();
            }


            NotaDeCredito notaDeCredito = new NotaDeCredito();

            notaDeCredito.motivo = notaDTO.motivo;
            notaDeCredito.factura = factura;
            notaDeCredito.nroComprobante = 0L;
            notaDeCredito.fecha = LocalDateTime.now();
            notaDeCredito.total = notaDTO.total;

            //actualizo la ctacte
            notaDeCredito.factura.socio.ctacte = notaDeCredito.factura.socio.ctacte.add(notaDeCredito.total);

            notaDeCredito = facturaService.cancelar(notaDeCredito);

            // Persistimos la factura (no es necesario llamar a socio.persist() si ya fue cargado desde la base de datos)
            notaDeCredito.persist();
            //factura.persist();

            //
            return Response.ok(notaDeCredito).build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.serverError().entity(e).build();
        }
    }

    private EstadoFactura asignarEstadoSegunTotales(Factura factura, NotaDeCreditoDTO notaDTO) throws Exception {

        //
        BigDecimal totalCancelado = notaDTO.total;

/*
        DEPRECATED

        BigDecimal totalPagos = new BigDecimal(0);
        for(Pago pago: factura.pagos){
            totalPagos = totalPagos.add(pago.monto);
        }

        if(notaDTO.total.compareTo(totalPagos) > 0){
            throw new Exception("La monto toal de la nota de crédito supera los pagos.El o los totales de las notadas de creditos superaría el monto total de la factura");
        }
        //
        for(NotaDeCredito notaDeCredito: factura.notasDeCredito){
            totalCancelado = totalCancelado.add(notaDeCredito.total);
        }

 */
        //Si el total cancelado supera el facturado, lanzamos una excepción porque no se podría
        if(totalCancelado.compareTo(factura.total) == 1){
            throw new Exception("El o los totales de las notadas de creditos superaría el monto total de la factura");
        } else if(totalCancelado.compareTo(factura.total) == 0){
            return EstadoFactura.CANCELADA;
        } else {
            return EstadoFactura.PARCIALMENTE_CANCELADA;
        }

    }

    @POST
    public Response facturar(FacturaDTO facturaDTO) {

        Factura factura = FacturaMapper.toEntity(facturaDTO);
        Socio socio = socioRepository.findById(facturaDTO.socio);

        // Verificamos si el socio existe
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }

        // Asignamos el socio a la factura
        factura.socio = socio;

        //
        Boolean usaSaldo = configuracionRepository.obtenerUtilizaSaldoParaPagarFactura();

        if( !usaSaldo ) {
           factura = facturaService.facturarSinSaldo(factura);
        }else{
           factura = facturaService.facturarConSaldo(factura);
        }


        try {
            factura = facturaService.facturar(factura);


            // Persistimos la factura (no es necesario llamar a socio.persist() si ya fue cargado desde la base de datos)
            factura.persist();

            // Devolvemos la factura como respuesta
            return Response.ok(factura).build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.serverError().entity(e).build();
        }
    }

    @GET
    @Path("/socio/{socioId}")
    public Response getPagosBySocio(@PathParam("socioId") Long socioId) {
        Socio socio = socioRepository.findById(socioId);
        if (socio != null) {

            List<Factura> facturas = facturaRepository.findBySocioId(socioId);

            if (facturas.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).entity("No se encontraron facturas para el socio con ID: " + socioId).build();
            }

            // Convertir las entidades Factura a FacturaDTO para evitar el problema de lazy loading
            List<FacturaDTO> facturaDTOs = facturas.stream()
                    .map(FacturaDTO::new)
                    .collect(Collectors.toList());

            return Response.ok(facturaDTOs).build();


        } else {
            //
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }
    }


    @GET
    @Path("/entidad/{entidadId}/{mes}/{anio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerFacturasEmitidasPorEntidadYPeriodo(
            @PathParam("entidadId") Long entidadId,
            @PathParam("mes") int mes,
            @PathParam("anio") int anio,
            @QueryParam("estado") String estado) {

        // Convertir mes y año en rango de fechas
        LocalDateTime inicioMes = LocalDateTime.of(anio, mes, 1, 0, 0);
        LocalDateTime finMes = inicioMes.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        EstadoFactura estadoFactura = (estado==null)
                ? EstadoFactura.EMITIDA
                :EstadoFactura.fromNombre(estado);

        // Buscar las facturas
        List<Factura> facturas = Factura.find(
                "estado = ?1 AND socio.entidadCrediticia.id = ?2 AND fecha >= ?3 AND fecha <= ?4",
                estadoFactura, entidadId, inicioMes, finMes).list();

        if (facturas.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No se encontraron facturas emitidas para la entidad crediticia en el periodo especificado.")
                    .build();
        }

        return Response.ok(facturas).build();
    }
}


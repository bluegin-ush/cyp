package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.dto.FacturaDTO;
import blugin.com.ar.dto.FacturaMapper;
import blugin.com.ar.dto.NotaDeCreditoDTO;
import blugin.com.ar.repository.FacturaRepository;
import blugin.com.ar.repository.SocioRepository;
import blugin.com.ar.service.FacturaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/facturar")
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

    @GET
    public Response foo(){
        facturaService.cargarConfiguraciones();
        return Response.ok().build();
    }
    @DELETE
    public Response cancelar(NotaDeCreditoDTO notaDTO) {

        Factura factura = facturaRepository.findById(notaDTO.factura);

        // Verificamos si el socio existe
        if (factura == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("La factura no existe").build();
        }

        try {
            factura.estado = EstadoFactura.CANCELADA;

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

        //actualizamos la ctacte
        socio.ctacte = socio.ctacte.subtract(factura.total);

        BigDecimal totalPagos = new BigDecimal(factura.total.intValue());

        // Verificamos si hay pagos
        if (factura.pagos != null && !factura.pagos.isEmpty()) {
            // Actualizamos la cuenta corriente (ctacte) del socio
            for (Pago p : factura.pagos) {
                socio.ctacte = socio.ctacte.add(p.monto);
                totalPagos = totalPagos.subtract(p.monto);
            }
        }

        //estalecemos el estado de la factura
        if (totalPagos.compareTo(BigDecimal.ZERO)<=0){
            factura.estado = EstadoFactura.PAGADA;
        }else {
            factura.estado = EstadoFactura.EMITIDA;
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
                return Response.status(Response.Status.NOT_FOUND).entity("No se encontraron facturas para el socio con ID: " + socioId).build();
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

    @POST
    @Path("/lote/{mes}/{anio}")
    @Transactional
    public Response generarFacturasPorLote(@PathParam("mes") int mes, @PathParam("anio") int anio, @QueryParam("prueba")Boolean prueba) {

        // Verificar si ya existe un lote generado para este mes y año
        LoteFactura loteExistente = LoteFactura.find("mes = ?1 and anio = ?2", mes, anio).firstResult();
        if (loteExistente != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Ya se ha generado un lote de facturas para este mes y año.")
                    .build();
        }

        // Crear un nuevo lote de facturas
        LoteFactura loteFactura = new LoteFactura(mes, anio);

        List<Socio> sociosActivos = Socio.list("activo", true);
        List<Factura> facturasGeneradas = new ArrayList<>();

        try {
            for (Socio socio : sociosActivos) {
                BigDecimal totalFactura = BigDecimal.ZERO;
                List<Item> items = new ArrayList<>();

                // Obtener los servicios asociados al socio
                for (Servicio servicio : socio.servicios) {
                    Item item = new Item();
                    item.concepto = servicio.descripcion;
                    item.cantidad = 1; // Asumimos cantidad 1 por servicio
                    item.precio = servicio.costo;
                    items.add(item);

                    // Sumar el costo del servicio al total de la factura
                    totalFactura = totalFactura.add(servicio.costo);
                }

                // Crear una nueva factura
                Factura factura = new Factura();
                factura.fecha = LocalDateTime.now();
                factura.socio = socio;
                factura.tipo = Factura.Tipo.C; // Factura tipo C por defecto
                factura.items = items;
                factura.total = totalFactura;
                factura.estado = EstadoFactura.EMITIDA; // Estado inicial

                //actualizamos el estado de la ctacte del socio
                factura.socio.ctacte = factura.socio.ctacte.add(totalFactura);

                // Relacionar los items con la factura
                for (Item item : items) {
                    item.factura = factura;
                }

                // Facturar en afip
                if(prueba==null || !prueba){
                    factura = facturaService.facturar(factura);
                }

                //
                loteFactura.agregarFactura(factura);


                facturasGeneradas.add(factura);
            }

            // Persistir el lote, si no es prueba!
            if(prueba==null || !prueba){
                loteFactura.persist();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.serverError().entity(e).build();
        }

        return Response.ok(facturasGeneradas).build();
    }
}


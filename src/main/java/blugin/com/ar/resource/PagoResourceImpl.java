package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.dto.PagoDTO;
import blugin.com.ar.repository.FacturaRepository;
import blugin.com.ar.repository.PagoRepository;
import blugin.com.ar.repository.SocioRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Path("/pago")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagoResourceImpl {
    private static final Logger log = LoggerFactory.getLogger(PagoResourceImpl.class);//implements PagoResource {

    @Inject
    PagoRepository pagoRepository;

    @Inject
    SocioRepository socioRepository;

    @Inject
    FacturaRepository facturaRepository;

    @POST
    @Transactional
    public Response crearPago(PagoDTO pagoDto) {
        // Obtener el socio relacionado con la factura del pago
        Factura factura = facturaRepository.findById(pagoDto.factura);

        Socio socio = factura.socio;

        if (socio != null) {
            // Actualizar la cuenta corriente (ctacte) del socio
            socio.ctacte = socio.ctacte.add(pagoDto.monto);

            Pago pago = new Pago();
            pago.factura = factura;
            pago.medioDePago = MedioDePago.valueOf(pagoDto.medioDePago);
            pago.monto = pagoDto.monto;

            if(pagoDto.fecha == null){
                pagoDto.fecha = LocalDateTime.now();
            }
            pago.fecha = pagoDto.fecha;

            //verificar si se cancela el total de la factura
            BigDecimal totalPagos = new BigDecimal(factura.total.intValue());


            //descuento este pago
            totalPagos = totalPagos.subtract(pago.monto);

            // Verificamos si hay pagos
            if (factura.pagos != null && !factura.pagos.isEmpty()) {
                // Actualizamos la cuenta corriente (ctacte) del socio
                for (Pago p : factura.pagos) {
                    totalPagos = totalPagos.subtract(p.monto);
                }
            }

            //estalecemos el estado de la factura
            if (totalPagos.compareTo(BigDecimal.ZERO)<=0){
                factura.estado = EstadoFactura.PAGADA;
            }

            // Persistir el pago
            pagoRepository.persist(pago);

            // Persistir la actualización del socio
            socio.persist();

            return Response.ok(pago).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado para la factura asociada").build();
        }
    }

    // Obtener todos los pagos
    @GET
    public List<Pago> listarPagos() {
        return pagoRepository.listAll();
    }

    // Obtener un pago por ID
    @GET
    @Path("/{id}")

    public Pago obtenerPago(@PathParam("id") Long id) {
        return pagoRepository.findById(id);
    }

    // Actualizar un pago existente
    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes("application/json")
    @Produces("application/json")
    public Response actualizarPago(@PathParam("id") Long id, Pago pago) {
        Pago pagoExistente = pagoRepository.findById(id);
        if (pagoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Pago no encontrado").build();
        }
        pagoExistente.factura = pago.factura;
        pagoExistente.monto = pago.monto;
        pagoExistente.fecha = pago.fecha;
        pagoExistente.medioDePago = pago.medioDePago;

        pagoRepository.persist(pagoExistente);
        return Response.ok(pagoExistente).build();
    }

    // Eliminar un pago por ID
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminarPago(@PathParam("id") Long id) {
        boolean eliminado = pagoRepository.deleteById(id);
        if (eliminado) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Pago no encontrado").build();
        }
    }

    @GET
    @Path("/socio/{socioId}")
    public Response getPagosBySocio(@PathParam("socioId") Long socioId) {
        Socio socio = socioRepository.findById(socioId);
        if (socio != null) {
            List<Pago> pagos = pagoRepository.findBySocioId(socioId);
            if (pagos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No se encontraron pagos para el socio con ID: " + socioId).build();
            }
            return Response.ok(pagos).build();
        } else {
            //
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }
    }
}

package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.MedioDePago;
import blugin.com.ar.cyp.model.Pago;
import blugin.com.ar.cyp.model.Socio;
import blugin.com.ar.dto.PagoDTO;
import blugin.com.ar.repository.FacturaRepository;
import blugin.com.ar.repository.PagoRepository;
import blugin.com.ar.repository.SocioRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pago")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagoResourceImpl {//implements PagoResource {

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
            pago.fecha = pagoDto.fecha;

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

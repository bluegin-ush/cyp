package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Pago;
import blugin.com.ar.cyp.model.Socio;
import blugin.com.ar.repository.PagoRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;

public interface PagoResource {//extends PanacheRepositoryResource<PagoRepository, Pago, Long> {

   /* @POST
    @Transactional
    default Response crearPago(Pago pago) {
        //
        Socio socio = pago.factura.socio;

        if (socio != null) {
            // Actualizar la cuenta corriente (ctacte) del socio
            socio.ctacte = socio.ctacte.add(pago.monto);

            // Persistir el pago
            pago.persist(pago);

            // Persistir la actualización del socio (no es necesario si ya está manejado por JPA)
            socio.persist();

            return Response.ok(pago).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado para la factura asociada").build();
        }
    }
    */
}

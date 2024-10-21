package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.LoteFactura;
import blugin.com.ar.repository.LoteFacturaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(paged = false)
public interface LoteFacturaResource extends PanacheRepositoryResource<LoteFacturaRepository, LoteFactura, Long> {

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

package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.LoteFactura;
import blugin.com.ar.repository.LoteFacturaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@ResourceProperties(paged = false)
public interface LoteFacturaResource extends PanacheRepositoryResource<LoteFacturaRepository, LoteFactura, Long> {

    @DELETE
    @Path("/{loteId}")
    default Response eliminarLote(@PathParam("loteId") Long loteId) {
        // Buscar el lote por su ID
        LoteFactura loteFactura = LoteFactura.findById(loteId);

        if (loteFactura == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("El lote de facturas no fue encontrado").build();
        }

        // Verificar si el lote tiene facturas asociadas
        if (loteFactura.facturas != null && !loteFactura.facturas.isEmpty()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("No se puede eliminar el lote, tiene facturas asociadas").build();
        }

        // Si no tiene facturas asociadas, proceder con la eliminación
        loteFactura.delete();
        return Response.ok("Lote eliminado con éxito").build();
    }
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

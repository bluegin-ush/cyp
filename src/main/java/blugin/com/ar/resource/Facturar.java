package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.Pago;
import blugin.com.ar.cyp.model.Servicio;
import blugin.com.ar.cyp.model.Socio;
import blugin.com.ar.dto.FacturaDTO;
import blugin.com.ar.dto.FacturaMapper;
import blugin.com.ar.fe.Main;
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

        // Verificamos si hay pagos
        if (factura.pagos != null && !factura.pagos.isEmpty()) {
            // Actualizamos la cuenta corriente (ctacte) del socio
            for (Pago p : factura.pagos) {
                socio.ctacte = socio.ctacte.add(p.monto);  // Suponiendo que ctacte es un BigDecimal
            }
        }

        /**
         * persistir y retornar
         * "nroComprobante": 0,
         *   "cae": "<long>",
         *   "vtoCae": "<date>",
         *
         */


        // Persistimos la factura (no es necesario llamar a socio.persist() si ya fue cargado desde la base de datos)
        factura.persist();

        // Devolvemos la factura como respuesta
        return Response.ok(facturaDTO).build();

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
}


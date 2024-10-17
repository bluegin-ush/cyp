package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.NotaDeCredito;
import blugin.com.ar.cyp.model.Socio;
import blugin.com.ar.dto.FacturaDTO;
import blugin.com.ar.repository.FacturaRepository;
import blugin.com.ar.repository.NotaDeCreditoRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/nota-de-credito")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotaDeCreditoResource {// extends PanacheRepositoryResource<NotaDeCreditoRepository, NotaDeCredito, Long> {

    @Inject
    NotaDeCreditoRepository notaDeCreditoRepository;

    @GET
    @Path("/factura/{facturaId}")
    public Response getNotasByFactura(@PathParam("facturaId") Long facturaId) {

        List<NotaDeCredito> notas = notaDeCreditoRepository.findByFacturaId(facturaId);

        if(notas!=null && !notas.isEmpty()) {
            return Response.ok(notas).build();
        }else{
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/socio/{socioId}")
    public Response getNotasBySocio(@PathParam("socioId") Long socioId) {
        List<NotaDeCredito> notas = notaDeCreditoRepository.findBySocioId(socioId);
        if(notas!=null && !notas.isEmpty()) {
            return Response.ok(notas).build();
        }else{
            return Response.noContent().build();
        }
    }

    @GET
    public Response getNotas() {
        return Response.ok(notaDeCreditoRepository.listAll()).build();
    }
}

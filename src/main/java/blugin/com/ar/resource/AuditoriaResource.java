package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.AuditoriaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ResourceProperties(paged = false)
@Path("/auditoria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuditoriaResource {


    @Inject
    AuditoriaRepository auditoriaRepository;

    @GET
    public Response obtenerTodas(@QueryParam("desde") LocalDate desde,
                                 @QueryParam("hasta") LocalDate hasta) {

        // Obtener el primer y último día del mes actual si los valores no son proporcionados
        LocalDate now = LocalDate.now();

        LocalDateTime desdeDateTime = (desde != null) ? desde.atStartOfDay() : now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime hastaDateTime = (hasta != null) ? hasta.atTime(LocalTime.MAX) : now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);

        return Response.ok(auditoriaRepository.obtenerPorFechas(desdeDateTime, hastaDateTime)).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerXId(@PathParam("id") Long id){


        return Response.ok(auditoriaRepository.findById(id)).build();
    }
}
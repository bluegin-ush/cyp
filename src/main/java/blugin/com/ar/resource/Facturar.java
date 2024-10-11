package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Servicio;
import blugin.com.ar.cyp.model.Socio;
import blugin.com.ar.fe.Main;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/facturar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class Facturar {

    @POST
    public Response fcturar() {

        System.out.println("FACTURANDO!!!");

        Main.main(null);
        //
        return Response.status(Response.Status.OK).build();
    }
}

package blugin.com.ar.resource;

import blugin.com.ar.fe.WSAAClient;
import blugin.com.ar.service.FacturaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("ping")
public class PingWS {

    private static final Logger log = LoggerFactory.getLogger(PingWS.class);

    @Inject
    FacturaService facturaService;

    @GET
    public Response pingAFIP(){
        log.info("PING --> AFIP services...");

        try {
            return Response.ok().entity(facturaService.ping()).build();
        }catch (Exception e){
            return Response.serverError().entity(e).build();
        }
    }
}

package blugin.com.ar.tools;

import blugin.com.ar.resource.AuditInterceptor;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.Providers;
import org.jboss.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Provider
public class ResponseAuditFilter implements ContainerResponseFilter {

    @Context
    private Providers providers;

    @Inject
    AuditInterceptor auditInterceptor;

    private static final Logger LOG = Logger.getLogger(ResponseAuditFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        String user = requestContext.getHeaderString("Authorization");
        int status = responseContext.getStatus();
        String estado="estado:"+status;
        //
        String body;
        try {
            body = getResponseBody(responseContext);
        }catch (IOException ioe){
            body = "error al obtener el body: " + ioe.getMessage();
        }

        if(!path.contains("/auditoria")) {
            auditInterceptor.audit(false, method, path, user, estado, null, body);
        }

        //LOG.infof("Response - Method: %s, Path: %s, Status: %d, Body: %s", method, path, status, body);
    }

    private String getResponseBody(ContainerResponseContext responseContext) throws IOException {

        Object entity = responseContext.getEntity();

        if (entity != null && responseContext.hasEntity()) {
            // Seleccionar el MessageBodyWriter adecuado para el tipo de entidad
            @SuppressWarnings("unchecked")
            MessageBodyWriter<Object> writer = (MessageBodyWriter<Object>) providers.getMessageBodyWriter(
                    entity.getClass(), null, null, MediaType.APPLICATION_JSON_TYPE);

            if (writer != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                writer.writeTo(entity, entity.getClass(), entity.getClass(), null, MediaType.APPLICATION_JSON_TYPE,
                        responseContext.getHeaders(), baos);
                return baos.toString();
            }
        }
        return "";
    }
}

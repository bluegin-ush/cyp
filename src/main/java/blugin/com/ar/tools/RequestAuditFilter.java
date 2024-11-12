package blugin.com.ar.tools;

import blugin.com.ar.resource.AuditInterceptor;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Provider

public class RequestAuditFilter implements ContainerRequestFilter {

    @Inject
    AuditInterceptor auditInterceptor;

    private static final Logger LOG = Logger.getLogger(RequestAuditFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {

        // Obtener Headers
        String headers = requestContext.getHeaders().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        // Obtener Query Parameters
        String queryParams = requestContext.getUriInfo().getQueryParameters().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", "));

        // Obtener el Body (si existe)
        String body;
        try {
            body = getRequestBody(requestContext);
        }catch (IOException ioe){
            body = "error al obtener el body: " + ioe.getMessage();
        }

        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        String user = requestContext.getHeaderString("Authorization"); // Ejemplo de captura de usuario

        if(!path.contains("/auditoria")) {
            auditInterceptor.audit(true, method, path, user, headers, queryParams, body);
        }

        //LOG.infof("Request - Method: %s, Path: %s, User: %s, Headers: %s, Parameters: %s, Body: %s", method, path, user, headers, queryParams, body);
    }

    private String getRequestBody(ContainerRequestContext requestContext) throws IOException {
        InputStream originalStream = requestContext.getEntityStream();

        // Leer y almacenar el cuerpo en un String
        String body = new String(originalStream.readAllBytes(), StandardCharsets.UTF_8);

        // Reemplazar el flujo de entrada de la entidad para que esté disponible para el procesamiento posterior
        requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));

        return body;
    }
}
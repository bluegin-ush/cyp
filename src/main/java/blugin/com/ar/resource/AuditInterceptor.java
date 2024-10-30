package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Auditoria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
@ApplicationScoped
public class AuditInterceptor {

    /*@Inject
    ResourceInfo resourceInfo;

    public void auditRequest(ContainerRequestContext requestContext, String body) {
        Auditoria auditoria = new Auditoria();
        auditoria.fecha = LocalDateTime.now();
        auditoria.usuario = requestContext.getHeaderString("Authorization");
        auditoria.metodo = requestContext.getMethod();
        auditoria.ruta = requestContext.getUriInfo().getPath();
        auditoria.headers = requestContext.getHeaders().toString();
        auditoria.queryParams = requestContext.getUriInfo().getQueryParameters().toString();
        auditoria.cuerpo = body;
        auditoria.entrada = true;
        
        // Persistir la entidad
        auditoria.persist();
    }

    public void auditResponse(ContainerResponseContext responseContext, String body) {

    }
    */
    @Transactional
    public void audit(Boolean request, String method, String path, String user, String headers, String queryParams, String body) {

        Auditoria auditoria = new Auditoria();
        auditoria.fecha = LocalDateTime.now();

        //revisar este atributo
        auditoria.usuario = user;

        auditoria.metodo = method;
        auditoria.ruta = path;
        auditoria.headers = headers;
        auditoria.cuerpo = body;
        auditoria.queryParams = queryParams;
        auditoria.entrada = request;

        // Persistir la entidad
        auditoria.persist();
    }
}

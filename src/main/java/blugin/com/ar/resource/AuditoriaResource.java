package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.AuditoriaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
public interface AuditoriaResource extends PanacheRepositoryResource<AuditoriaRepository, Auditoria, Long> { }
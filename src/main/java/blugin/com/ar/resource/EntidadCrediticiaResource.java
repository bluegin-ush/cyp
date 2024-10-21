package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.EntidadCrediticiaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(paged = false)
public interface EntidadCrediticiaResource extends PanacheRepositoryResource<EntidadCrediticiaRepository, EntidadCrediticia, Long> { }

package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.EntidadCrediticiaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
public interface EntidadCrediticiaResource extends PanacheRepositoryResource<EntidadCrediticiaRepository, EntidadCrediticia, Long> { }

package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.ConfiguracionRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(paged = false)
public interface ConfiguracionResource extends PanacheRepositoryResource<ConfiguracionRepository, Configuracion, Long> {}
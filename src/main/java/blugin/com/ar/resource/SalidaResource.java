package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Salida;
import blugin.com.ar.repository.SalidaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(paged = false)
public interface SalidaResource extends PanacheRepositoryResource<SalidaRepository, Salida, Long> { }

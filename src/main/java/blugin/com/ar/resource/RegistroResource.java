package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Registro;
import blugin.com.ar.repository.RegistroRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface RegistroResource extends PanacheRepositoryResource<RegistroRepository, Registro, Long> { }

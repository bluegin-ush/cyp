package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.ServicioRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface ServicioResource extends PanacheRepositoryResource<ServicioRepository, Servicio, Long> { }

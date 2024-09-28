package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.UsuarioRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface UsuarioResource extends PanacheRepositoryResource<UsuarioRepository, Usuario, Long> { }

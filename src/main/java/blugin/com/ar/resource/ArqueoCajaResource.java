package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.ArqueoCajaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
public interface ArqueoCajaResource extends PanacheRepositoryResource<ArqueoCajaRepository, ArqueoCaja, Long> { }
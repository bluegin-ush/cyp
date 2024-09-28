package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Pago;
import blugin.com.ar.repository.PagoRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface PagoResource extends PanacheRepositoryResource<PagoRepository, Pago, Long> { }

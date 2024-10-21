package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.ArchivoProcesamiento;
import blugin.com.ar.repository.ArchivoProcesamientoRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(paged = false)
public interface ArchivoProcesamientoResource extends PanacheRepositoryResource<ArchivoProcesamientoRepository, ArchivoProcesamiento, Long> { }
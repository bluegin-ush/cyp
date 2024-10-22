package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.Archivo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArchivoRepository implements PanacheRepository<Archivo> { }

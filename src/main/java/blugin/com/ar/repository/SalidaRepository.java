package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.Salida;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SalidaRepository implements PanacheRepository<Salida>{ }

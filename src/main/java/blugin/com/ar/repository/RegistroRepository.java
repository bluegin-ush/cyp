package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RegistroRepository implements PanacheRepository<Registro> { }

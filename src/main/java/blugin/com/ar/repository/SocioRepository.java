package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SocioRepository implements PanacheRepository<Socio> {
    public List<Socio> findByEntidadId(Long entidadId) {
        return find("entidadCrediticia.id", entidadId).list();
    }
}

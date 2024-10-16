package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FacturaRepository implements PanacheRepository<Factura> {

   // Consulta para obtener todos los pagos de un socio por su ID
    public List<Factura> findBySocioId(Long socioId) {
        //return find("socio.id", socioId).list();
        return find("select f from Factura f left join fetch f.items where f.socio.id = ?1", socioId).list();
    }

}

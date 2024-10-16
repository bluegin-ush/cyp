package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PagoRepository implements PanacheRepository<Pago> {

    // Consulta para obtener todos los pagos de un socio por su ID
    public List<Pago> findBySocioId(Long socioId) {
        return find("factura.socio.id", socioId).list();
    }

}
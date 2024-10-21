package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.LoteFactura;
import blugin.com.ar.cyp.model.NotaDeCredito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class LoteFacturaRepository implements PanacheRepository<LoteFactura> {

    /*
    // Consulta para obtener todos los pagos de un socio por su ID
    public List<NotaDeCredito> findBySocioId(Long socioId) {
        return find("factura.socio.id", socioId).list();
        //return find("select f from Factura f left join fetch f.items where f.socio.id = ?1", socioId).list();
    }

    public List<NotaDeCredito> findByFacturaId(Long facturaId) {
        return find("factura.id", facturaId).list();
        //return find("select f from Factura f left join fetch f.items where f.socio.id = ?1", socioId).list();
    }
    */
}

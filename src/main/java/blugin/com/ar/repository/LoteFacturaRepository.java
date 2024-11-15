package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.LoteFactura;
import blugin.com.ar.cyp.model.NotaDeCredito;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class LoteFacturaRepository implements PanacheRepository<LoteFactura> {

    public boolean existeLoteEnMesActual() {
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finMes = inicioMes.plusMonths(1).minusNanos(1);

        return find("fechaGeneracion >= ?1 and fechaGeneracion <= ?2", inicioMes, finMes).count() > 0;
    }
}

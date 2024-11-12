package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class FacturaRepository implements PanacheRepository<Factura> {

   // Consulta para obtener todos los pagos de un socio por su ID
    public List<Factura> findBySocioId(Long socioId) {
        //return find("socio.id", socioId).list();
        return find("select f from Factura f left join fetch f.items where f.socio.id = ?1", socioId).list();
    }

    public List<Factura> buscarFacturasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime fechaInicioTime = fechaInicio.atStartOfDay();
        LocalDateTime fechaFinTime = fechaFin.atTime(LocalTime.MAX);
        return find("#Factura.buscarEntreFechas", Parameters.with("fechaInicio", fechaInicioTime).and("fechaFin", fechaFinTime)).list();
    }

    public List<Factura> buscarFacturasEntreFechasPorSocio(Long socioId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        return find("#Factura.buscarEntreFechasXSocio",
                Parameters.with("socioId", socioId).and("fechaInicio", fechaInicio).and("fechaFin", fechaFin)).list();
    }
}

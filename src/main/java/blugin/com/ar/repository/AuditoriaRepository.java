package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*; // Asegúrate de importar tus modelos correctamente
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AuditoriaRepository implements PanacheRepository<Auditoria> {

    public List<Auditoria> obtenerPorFechas(LocalDateTime desde, LocalDateTime hasta) {

        return find("fecha >= ?1 and fecha <= ?2", desde, hasta).list();
    }
}
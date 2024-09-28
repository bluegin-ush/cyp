package blugin.com.ar.cyp.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
public class ArqueoCaja extends PanacheEntity {
    public LocalDate fecha;
    public String detalle;
    public BigDecimal total;
}


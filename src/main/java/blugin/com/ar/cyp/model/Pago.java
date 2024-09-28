package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pago extends PanacheEntity {

    @ManyToOne
    public Factura factura;

    public String concepto;
    public LocalDateTime fecha;
    public BigDecimal monto;
}
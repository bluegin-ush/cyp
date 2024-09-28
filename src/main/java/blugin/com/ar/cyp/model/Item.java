package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class Item extends PanacheEntity {

    @ManyToOne
    public Factura factura;

    public String concepto;
    public Integer cantidad;
    public BigDecimal precio;
}

package blugin.com.ar.cyp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Entity
public class Item extends PanacheEntity {

    private static final Logger log = LoggerFactory.getLogger(Item.class);
    @ManyToOne
    @JsonBackReference
    public Factura factura;

    public String concepto;
    public Integer cantidad;
    public BigDecimal precio;

    public Item(){

    }

    public Long getFacturaId(){
        return this.factura.id;
    }
}

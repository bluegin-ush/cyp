package blugin.com.ar.cyp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pago extends PanacheEntity {

    private static final Logger log = LoggerFactory.getLogger(Pago.class);
    @ManyToOne
    @JsonBackReference
    public Factura factura;

    //public String concepto;
    public LocalDateTime fecha;
    public BigDecimal monto;

    @Enumerated(EnumType.STRING)
    public MedioDePago medioDePago;

    public Pago(){

    }

    public Long getFacturaId(){
        return this.factura.id;
    }
}
package blugin.com.ar.cyp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class NotaDeCredito extends PanacheEntity {

    public LocalDateTime fecha;
    public Long nroComprobante;
    public String cae;
    public LocalDate vtoCae;
    public BigDecimal total;
    public String motivo;
    
    @ManyToOne
    @JsonBackReference
    public Factura factura;

}
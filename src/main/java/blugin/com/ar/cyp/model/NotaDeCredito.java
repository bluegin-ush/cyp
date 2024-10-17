package blugin.com.ar.cyp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class NotaDeCredito extends PanacheEntity {

    private static final Logger log = LoggerFactory.getLogger(NotaDeCredito.class);
    public LocalDateTime fecha;
    public Long nroComprobante;
    public String cae;
    public LocalDate vtoCae;
    public BigDecimal total;
    public String motivo;
    
    @ManyToOne
    @JsonBackReference
    public Factura factura;

    public long getFacturaId(){
        return factura.id;
    }
}
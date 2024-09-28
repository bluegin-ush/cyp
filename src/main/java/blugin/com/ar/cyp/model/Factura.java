package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Factura extends PanacheEntity {

    public LocalDateTime fecha; 

    public enum Tipo {
        C // Factura tipo C
    }

    @Enumerated(EnumType.STRING)
    public Tipo tipo;

    public Integer medioPago; // Considerar usar un Enum o entidad si hay más opciones

    @ManyToOne
    public Socio socio;

    public Integer nroComprobante;
    public Long cae; // Cambiar a Long para almacenar el CAE
    public LocalDate vtoCae;
    public BigDecimal total;

    @OneToMany(mappedBy = "factura")
    public List<Item> items; // Relación con Item

    @OneToMany(mappedBy = "factura")
    public List<Pago> pagos; // Relación con Pago
}
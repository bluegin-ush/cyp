package blugin.com.ar.cyp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura extends PanacheEntity {

    private static final Logger log = LoggerFactory.getLogger(Factura.class);
    public LocalDateTime fecha;

    public enum Tipo {
        C // Factura tipo C
    }

    @Enumerated(EnumType.STRING)
    public Tipo tipo;

    @ManyToOne
    public Socio socio;

    public Long nroComprobante;
    public String cae;
    public LocalDate vtoCae;
    public BigDecimal total;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonDeserialize(contentAs = Item.class)
    @JsonManagedReference
    public List<Item> items;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonDeserialize(contentAs = Pago.class)
    @JsonManagedReference
    public List<Pago> pagos;

    // Estado de la factura - "Emitida", "Pagada", "Cancelada"
    @Enumerated(EnumType.STRING)
    public EstadoFactura estado;  //

    // Datos relacionados con la cancelación
    //public LocalDate fechaCancelacion;
    //public String motivoCancelacion;
    //public long notaCreditoId;
}
package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Registra las salidas de dinero que la asociación realiza.
 */
@Entity
public class Salida extends PanacheEntity {

    public LocalDateTime fecha;

    // Tipo de salida: efectivo, transferencia, débito, etc.
    @Enumerated(EnumType.STRING)
    public TipoSalida tipo;

    public BigDecimal monto;

    public String descripcion;

}


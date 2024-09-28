package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Servicio extends PanacheEntity {

    @Column(unique = true)
    public String codigo;
    public String descripcion;
    public BigDecimal costo;
}
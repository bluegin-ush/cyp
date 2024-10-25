package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Configuracion extends PanacheEntity {
    public String clave;

    @Column(columnDefinition = "TEXT")
    public String valor;
}

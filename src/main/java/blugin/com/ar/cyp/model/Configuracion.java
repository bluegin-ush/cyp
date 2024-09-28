package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Configuracion extends PanacheEntity {
    public String clave;
    public String valor;
}

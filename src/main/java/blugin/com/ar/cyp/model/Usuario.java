package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;


@Entity
public class Usuario extends PanacheEntity {
    public String usuario;
    public String nombre;
}
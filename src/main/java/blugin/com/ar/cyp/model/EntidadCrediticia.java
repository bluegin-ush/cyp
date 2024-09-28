package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

@Entity
public class EntidadCrediticia extends PanacheEntity {
    public String tipo;
    public String nombre;
    public Boolean archivo;
    public String cuit;
    public String contacto;
}

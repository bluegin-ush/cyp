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
    public Long cuit;
    public String contacto;

    @Override
    public String toString() {
        return "{ id=" + id +
                "tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", archivo=" + archivo +
                ", cuit=" + cuit +
                ", contacto='" + contacto + " }";
    }
}

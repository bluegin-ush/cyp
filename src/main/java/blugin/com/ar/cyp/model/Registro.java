package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Registro extends PanacheEntity {

    @ManyToOne
    private Socio socio;

    public LocalDate fecha;

    public enum Tipo {
        ALTA, BAJA
    }

    @Enumerated(EnumType.STRING)
    public Tipo tipo;

    public String motivo;

    public void setSocio(Socio socio){
        this.socio = socio;
    }
}
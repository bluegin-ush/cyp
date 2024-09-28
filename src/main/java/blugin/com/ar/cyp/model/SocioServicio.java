package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

//@Entity DEPRECATED
public class SocioServicio extends PanacheEntity {

    @ManyToOne
    public Socio socio;

    @ManyToOne
    public Servicio servicio;
}
package blugin.com.ar.cyp.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
@Entity
public class Auditoria extends PanacheEntity {

    public LocalDateTime fecha;
    public String metodo;
    public String ruta;
    public String usuario;
    @Column(columnDefinition = "TEXT")
    public String headers;
    public String queryParams;
    public Boolean entrada;
    @Column(columnDefinition = "TEXT")
    public String cuerpo;
}
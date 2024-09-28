package blugin.com.ar.cyp.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
@Entity
public class Auditoria extends PanacheEntity {
    public LocalDateTime fecha; 
    public String evento;
}
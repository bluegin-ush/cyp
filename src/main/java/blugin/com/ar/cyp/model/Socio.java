package blugin.com.ar.cyp.model;

import blugin.com.ar.dto.FacturaDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Socio extends PanacheEntity {

    private static final Logger log = LoggerFactory.getLogger(Socio.class);

    public Long nro;

    public String nombre;

    public String apellido;
    public String tipoDoc; // Tipo de documento
    public Long numDoc; // Número de documento
    public String correo;

    public String telefono;

    public String domicilio;

    @ManyToOne
    public EntidadCrediticia entidadCrediticia;

    //16 dígitos
    public String tarjetaNum;
    //MM/YY
    public String tarjetaVto;

    public Boolean activo;
    public BigDecimal ctacte; // Cuenta corriente

    @OneToMany(mappedBy = "socio", fetch = FetchType.EAGER)
    private List<Registro> registros; // Relación con Registro

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "socio_servicio",
        joinColumns = @JoinColumn(name = "socio_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    public List<Servicio> servicios; // Relación con Servicio

    public void agregarRegistro(Registro registro) {
        if (registros == null){
            registros = new ArrayList<>();
        }
        registros.add(registro);
    }

    public List<Registro> getRegistros(){

        return registros;
    }

    @Transient
   public Boolean tieneDeuda;

}
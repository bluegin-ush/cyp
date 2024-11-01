package blugin.com.ar.cyp.model;

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

    /*
    public void setTarjetaCod(String tarjetaCod) {
        //TODO validar que sean 3 dígitos
        this.tarjetaCod = tarjetaCod;
    }

    public void setTarjetaNum(String tarjetaNum) {
        //TODO validar que sean 16 dígitos
        this.tarjetaNum = tarjetaNum;
    }

    public void setTarjetaVto(String tarjetaVto) {
        //TODO validar que el vencimiento sea en el formato MM/YY
        this.tarjetaVto = tarjetaVto;
    }

    public String getEntidadCrediticia() {

        if(entidadCrediticia!= null){
            return String.format("Entidad : %s - {%s-%s-%s}",entidadCrediticia.toString(), tarjetaNum, tarjetaVto, tarjetaCod );
        }else{
            return "sin-asociar";
        }
    }

    public void setEntidadCrediticia(EntidadCrediticia entidadCrediticia) {
        this.entidadCrediticia = entidadCrediticia;
    }
    */

}
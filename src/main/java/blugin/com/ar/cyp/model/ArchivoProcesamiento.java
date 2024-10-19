package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ArchivoProcesamiento extends PanacheEntity {

    @ManyToOne
    public EntidadCrediticia entidadCrediticia;

    public LocalDateTime fechaGeneracion;

    //puede autogenrarse con YYYYMMDD-Entidad.txt
    public String nombreArchivo;

    /*
    // Estado del archivo: GENERADO, ENVIADO, PROCESADO, ERROR
    @Enumerated(EnumType.STRING)
    public EstadoArchivo estado;
    */

    /*
    // Detalle de los errores (si los hubo) al procesar el archivo
    @Lob
    public String detalleErrores;
    */

    @OneToMany(mappedBy = "archivoProcesamiento")
    public List<Factura> facturas;

}
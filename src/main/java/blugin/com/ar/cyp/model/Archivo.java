package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Archivo extends PanacheEntity {

    @ManyToOne
    public EntidadCrediticia entidadCrediticia;

    public LocalDateTime fechaGeneracion;

    // Estado del archivo: GENERADO, ENVIADO, PROCESADO, ERROR
    @Enumerated(EnumType.STRING)
    public EstadoArchivo estado;

    @Lob
    public String archivo;

    // Detalle de los errores (si los hubo) al procesar el archivo
    @Lob
    public String detalleErrores;

    public List<Long> idFacturasRechazadas;

    public List<Long> idFacturasProcesadas;

    @OneToMany(mappedBy = "archivo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Factura> facturas;

    public BigDecimal importeTotal;

    public Archivo(){
        facturas = new ArrayList<>();
        idFacturasRechazadas = new ArrayList<>();
        idFacturasProcesadas = new ArrayList<>();
    }
    public void agregarFactura(Factura factura) {
        facturas.add(factura);
        factura.setArchivo(this);
    }

}
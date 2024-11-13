package blugin.com.ar.cyp.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LoteFactura extends PanacheEntity {

    private static final Logger log = LoggerFactory.getLogger(LoteFactura.class);
    public int mes;
    public int anio;
    public LocalDateTime fechaGeneracion;
    public EstadoLote estado;
    public int progreso;

    @OneToMany(mappedBy = "loteFactura", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Factura> facturas;

    public List<Long> idFacturasEmitidas;
    public List<Long> idFacturasErroneas;

    public LoteFactura() {}

    public LoteFactura(int mes, int anio) {
        this.mes = mes;
        this.anio = anio;
        this.fechaGeneracion = LocalDateTime.now();
        idFacturasEmitidas = new ArrayList<>();
        idFacturasErroneas = new ArrayList<>();
    }

    public void agregarFactura(Factura factura) {
        if(facturas == null){
            facturas = new ArrayList<>();
        }
        facturas.add(factura);
        factura.setLoteFactura(this);
    }
}
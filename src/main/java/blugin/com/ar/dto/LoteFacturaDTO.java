package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.LoteFactura;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LoteFacturaDTO {
    public Long id;
    public LocalDateTime fechaGeneracion;
    public String estado;
    public int progreso;
    //public List<Long> facturasIds;
    public List<Long> idFacturasEmitidas;
    public List<Long> idFacturasErroneas;

    public LoteFacturaDTO() {
    }

    // Constructor que toma la entidad LoteFactura
    public LoteFacturaDTO(LoteFactura loteFactura) {
        this.id = loteFactura.id;
        this.fechaGeneracion = loteFactura.fechaGeneracion;
        this.estado = loteFactura.estado.name();
        this.progreso = loteFactura.progreso;
        //this.facturasIds = loteFactura.facturas.stream()
        //        .map(f -> f.id)
        //        .collect(Collectors.toList());
        this.idFacturasEmitidas = loteFactura.idFacturasEmitidas;
        this.idFacturasErroneas = loteFactura.idFacturasErroneas;
    }
}
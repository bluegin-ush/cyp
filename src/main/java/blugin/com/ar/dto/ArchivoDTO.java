package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.Archivo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ArchivoDTO {
    public Long id;
    public Long entidadCrediticiaId;
    public LocalDateTime fechaGeneracion;
    public String estado;
    //public String archivo;
    public String detalleErrores;
    public List<Long> idFacturasRechazadas;
    public List<Long> idFacturas;
    public BigDecimal importeTotal;

    public ArchivoDTO() {
    }

    public ArchivoDTO(Archivo archivo) {
        this.id = archivo.id;
        this.entidadCrediticiaId = archivo.entidadCrediticia != null ? archivo.entidadCrediticia.id : null;
        this.fechaGeneracion = archivo.fechaGeneracion;
        this.estado = archivo.estado.name();
        //this.archivo = archivo.archivo;
        this.detalleErrores = archivo.detalleErrores;
        this.idFacturasRechazadas = archivo.idFacturasRechazadas;
        this.idFacturas = archivo.facturas.stream()
                .map(f -> f.id)
                .collect(Collectors.toList());
        this.importeTotal = archivo.importeTotal;
    }
}
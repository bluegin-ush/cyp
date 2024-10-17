package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.EstadoFactura;
import blugin.com.ar.cyp.model.Factura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FacturaDTO {

    public Long facturaId;

    public LocalDateTime fecha;
    public String tipo;
    public Long socio;
    public BigDecimal total;
    public List<PagoDTO> pagos;
    public List<ItemDTO> items;

    public String estado;
    public String qr;

    public FacturaDTO(){

    }

    // Constructor que toma la entidad Factura
    public FacturaDTO(Factura factura) {
        this.facturaId = factura.id;
        //this.numero = factura.numero;

        this.fecha = factura.fecha;
        this.tipo = factura.tipo.name();
        this.socio = factura.socio.id;
        this.total = factura.total;

        this.items = factura.items.stream()
                .map(FacturaMapper::toItemDTO)
                .collect(Collectors.toList());

        this.pagos = factura.pagos.stream()
                .map(FacturaMapper::toPagoDTO)
                .collect(Collectors.toList());

        this.estado = factura.estado.name();

        this.qr = factura.qr;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public List<PagoDTO> getPagos() {
        return pagos;
    }
}

package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.Factura;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FacturaDTO {

    private Long facturaId;

    public LocalDateTime fecha;
    public String tipo;
    public Long socio;
    public BigDecimal total;
    public List<PagoDTO> pagos;
    public List<ItemDTO> items;

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

    }

    public Long getFacturaId(){
        return this.facturaId;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public List<PagoDTO> getPagos() {
        return pagos;
    }
}
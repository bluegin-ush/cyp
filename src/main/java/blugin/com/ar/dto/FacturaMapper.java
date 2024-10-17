package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.Item;
import blugin.com.ar.cyp.model.MedioDePago;
import blugin.com.ar.cyp.model.Pago;
import org.hibernate.boot.archive.spi.ArchiveEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class FacturaMapper {

    private static final Logger log = LoggerFactory.getLogger(FacturaMapper.class);

    public static Factura toEntity(FacturaDTO dto) {
        Factura factura = new Factura();

        if(dto.fecha == null){
            dto.fecha = LocalDateTime.now();
        }

        factura.fecha = dto.fecha;
        factura.total = dto.total;
        factura.tipo = Factura.Tipo.valueOf(dto.tipo);

        if(dto.items != null) {
            factura.items = dto.items.stream()
                    .map(FacturaMapper::toEntity)
                    .collect(Collectors.toList());

            // Relacionar cada pago con la factura
            for (Item item : factura.items) {
                item.factura = factura;
            }

        }

        if (dto.pagos != null) {
            factura.pagos = dto.pagos.stream()
                .map(FacturaMapper::toEntity)
                .collect(Collectors.toList());
            
            // Relacionar cada pago con la factura
            for (Pago pago : factura.pagos) {
                pago.factura = factura;
            }
        }
        
        return factura;
    }

    public static Pago toEntity(PagoDTO dto) {
        Pago pago = new Pago();
        pago.monto = dto.monto;

        if(dto.fecha == null){
            dto.fecha = LocalDateTime.now();
        }

        pago.fecha = dto.fecha;
        pago.medioDePago = MedioDePago.valueOf(dto.medioDePago);
        return pago;
    }

    public static Item toEntity(ItemDTO dto) {
        Item item = new Item();
        item.cantidad = dto.cantidad;
        item.concepto = dto.concepto;
        item.precio = dto.precio;
        return item;
    }


    public static ItemDTO toItemDTO(Item item) {

        ItemDTO dto = new ItemDTO();
        dto.cantidad = item.cantidad;
        dto.concepto = item.concepto;
        dto.precio = item.precio;

        return dto;
    }

    public static PagoDTO toPagoDTO(Pago pago) {

        PagoDTO dto = new PagoDTO();
        dto.factura = pago.factura.id;
        dto.fecha = pago.fecha;
        dto.medioDePago = pago.medioDePago.name();
        dto.monto = pago.monto;

        return dto;
    }
}

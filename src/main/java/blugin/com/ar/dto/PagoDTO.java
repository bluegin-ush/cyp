package blugin.com.ar.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PagoDTO {

    public BigDecimal monto;
    public LocalDateTime fecha;
    public String medioDePago;
    public Long factura;
}
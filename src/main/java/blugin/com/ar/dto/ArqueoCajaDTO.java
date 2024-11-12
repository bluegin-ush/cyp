package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.MedioDePago;
import blugin.com.ar.cyp.model.Pago;
import blugin.com.ar.cyp.model.Salida;
import blugin.com.ar.cyp.model.TipoSalida;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ArqueoCajaDTO {
    public BigDecimal totalEntradas;
    public BigDecimal totalSalidas;
    public BigDecimal saldoFinal;

    // Map con los ingresos agrupados por tipo de pago
    public Map<MedioDePago, BigDecimal> ingresosPorTipo;

    // Map con las salidas agrupadas por tipo de salida
    public Map<TipoSalida, BigDecimal> salidasPorTipo;

    public List<Pago> pagos;

    public List<Salida> salidas;
}
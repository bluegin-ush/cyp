package blugin.com.ar.wsfe.wrappers;

import java.math.BigDecimal;

public class Tributo{

    private final short codigo;
    private final BigDecimal alicuota;
    private final BigDecimal baseImponible;
    private final String descripcion;

    public Tributo(short codigo, BigDecimal alicuota, BigDecimal baseImponible, String descripcion) {
        this.codigo = codigo;
        this.alicuota = alicuota;
        this.baseImponible = baseImponible;
        this.descripcion = descripcion;
    }

    public blugin.com.ar.wsfe.Tributo aTributo() {
        blugin.com.ar.wsfe.Tributo tributo = new blugin.com.ar.wsfe.Tributo();
        tributo.setAlic(alicuota.doubleValue());
        tributo.setBaseImp(baseImponible.doubleValue());
        tributo.setDesc(descripcion);
        tributo.setId(codigo);
        tributo.setImporte(Numeros.doubleConPrecision(importe()));
        return tributo;
    }

    public BigDecimal importe() {
        return baseImponible.multiply(alicuota);
    }

}


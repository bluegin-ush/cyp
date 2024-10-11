package blugin.com.ar.wsfe.wrappers;

public class Comprobante {

    private final int puntoVenta;
    private final long numero;

    private final TiposComprobante tipo;

    public Comprobante() {
        this(1, 1, TiposComprobante.FACTURA_C);
    }
    public Comprobante(int puntoVenta, long numero, TiposComprobante tipo) {
        this.puntoVenta = puntoVenta;
        this.numero = numero;
        this.tipo = tipo;
    }

    public static int puntoVentaDefault() {
        return 1;
    }

    public int puntoVenta() {
            return puntoVenta;
        }

    public long numero() {
            return numero;
        }

    public TiposComprobante tipo(){ return tipo; }

    }


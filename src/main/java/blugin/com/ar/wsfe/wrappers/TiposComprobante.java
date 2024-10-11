package blugin.com.ar.wsfe.wrappers;

public enum TiposComprobante {
    FACTURA_A(1),
    FACTURA_B(6),
    NOTA_DEBITO_B(7),
    NOTA_CREDITO_B(8),
    FACTURA_C(11),

    NOTA_CREDITO_C(13);

    private final int codigo;

    private TiposComprobante(int codigo) {
        this.codigo = codigo;
    }

    public int codigo() {
        return codigo;
    }
}
package blugin.com.ar.wsfe.wrappers;

public enum TiposDocumento {

    CUIT(80),
    DNI(96),
    CDI(87),
    OTRO(99);

    private final int codigo;

    private TiposDocumento(int codigo) {
        this.codigo = codigo;
    }

    public int codigo() {
        return codigo;
    }

}
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

    public static TiposDocumento getTipo(String tipo){
        if(tipo == null || tipo.isEmpty()){
            return DNI;
        } else if (tipo.toLowerCase().equals("dni") || tipo.toLowerCase().equals("d")) {
            return DNI;
        } else if (tipo.toLowerCase().equals("cuit")|| tipo.toLowerCase().equals("c")) {
            return CUIT;
        } else {
            return DNI;
        }
    }
}
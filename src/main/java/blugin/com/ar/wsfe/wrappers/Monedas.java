package blugin.com.ar.wsfe.wrappers;

public enum Monedas {

    PESOS_ARGENTINOS("PES");

    private final String codigo;

    private Monedas(String codigo) {
        this.codigo = codigo;
    }

    public String codigo() {
        return codigo;
    }

}
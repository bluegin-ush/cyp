package blugin.com.ar.cyp.model;

public enum TipoSalida {
    EFECTIVO("efectivo"),
    TRANSFERENCIA("transferencia"),
    DEBITO("debito");

    private final String nombre;

    TipoSalida(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // Método para obtener el enum a partir del nombre
    public static TipoSalida fromNombre(String nombre) {
        for (TipoSalida tipo : TipoSalida.values()) {
            if (tipo.getNombre().equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No existe el tipo de salida con nombre: " + nombre);
    }
}
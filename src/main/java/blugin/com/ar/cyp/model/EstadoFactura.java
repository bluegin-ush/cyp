package blugin.com.ar.cyp.model;

public enum EstadoFactura {
        PROCESO("proceso"),
        EMITIDA("emitida"),
        PAGADA("pagada"),
        CANCELADA("cancelada"),
        PARCIALMENTE_CANCELADA("parcialmente-cancelada");

    private final String nombre;

    EstadoFactura(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // Método para obtener el enum a partir del nombre
    public static EstadoFactura fromNombre(String nombre) {
        for (EstadoFactura medio : EstadoFactura.values()) {
            if (medio.getNombre().equalsIgnoreCase(nombre)) {
                return medio;
            }
        }
        throw new IllegalArgumentException("No existe el estado de factura con nombre: " + nombre);
    }
    }
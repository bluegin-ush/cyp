package blugin.com.ar.cyp.model;

public enum MedioDePago {
        EFECTIVO("efectivo"),
        TARJETA_CREDITO("tarjeta-credito"),
        TARJETA_DEBITO("tarjeta-debito"),
        TRANSFERENCIA_BANCARIA("transferencia-bancaria");

        private final String nombre;

        MedioDePago(String nombre) {
                this.nombre = nombre;
        }

        public String getNombre() {
                return nombre;
        }

        // Método para obtener el enum a partir del nombre
        public static MedioDePago fromNombre(String nombre) {
                for (MedioDePago medio : MedioDePago.values()) {
                        if (medio.getNombre().equalsIgnoreCase(nombre)) {
                                return medio;
                        }
                }
                throw new IllegalArgumentException("No existe el medio de pago con nombre: " + nombre);
        }
    }
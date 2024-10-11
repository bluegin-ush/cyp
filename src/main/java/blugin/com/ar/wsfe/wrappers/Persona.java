package blugin.com.ar.wsfe.wrappers;

public class Persona {

        private final TiposDocumento tipo;
        private final long numero;

        public Persona(TiposDocumento tipo, long numero) {
            this.tipo = tipo;
            this.numero = numero;
        }

        public TiposDocumento tipo() {
            return tipo;
        }

        public long numero() {
            return numero;
        }

}

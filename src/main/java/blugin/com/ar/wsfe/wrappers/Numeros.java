package blugin.com.ar.wsfe.wrappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Numeros {

    /**
     * Devuelve un double con la escala requerida por el web service de la AFIP.
     *
     * @param valor
     * @return
     * @see sección 4.21 Margen de error mediante ( Error Absoluto y Error Relativo ) del web service.
     */
    public static double doubleConPrecision(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}

package blugin.com.ar.fe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FacturaDatosAFIP {

    public int nroComprobante;
    public String cae;
    public String vto;

    /*public LocalDate getVto(){

        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        //
        return LocalDate.parse(vto, formatter);
    }*/

    public static LocalDate getVto(String vto){

        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        //
        return LocalDate.parse(vto, formatter);
    }

    public static String getVto(LocalDate vto){

        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        //
        return vto.format(formatter);
    }
}

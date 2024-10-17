package blugin.com.ar.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FacturaQR {
    public int ver;
    public String fecha;
    public long cuit;
    public int ptoVta;
    public int tipoCmp;
    public long nroCmp;
    public BigDecimal importe;
    public String moneda;
    public double ctz;
    public int tipoDocRec;
    public long nroDocRec;
    public String tipoCodAut;
    public long codAut;

    // Constructor vacío para Jackson (es necesario)
    public FacturaQR() {
    }

    // Constructor con parámetros
    public FacturaQR(int ver, String fecha, long cuit, int ptoVta, int tipoCmp, long nroCmp, BigDecimal importe,
                     String moneda, double ctz, int tipoDocRec, long nroDocRec, String tipoCodAut, long codAut) {
        this.ver = ver;
        this.fecha = fecha;
        this.cuit = cuit;
        this.ptoVta = ptoVta;
        this.tipoCmp = tipoCmp;
        this.nroCmp = nroCmp;
        this.importe = importe;
        this.moneda = moneda;
        this.ctz = ctz;
        this.tipoDocRec = tipoDocRec;
        this.nroDocRec = nroDocRec;
        this.tipoCodAut = tipoCodAut;
        this.codAut = codAut;
    }

    // Método para convertir el objeto Factura a JSON
    public static String convertirAFacturaJSON(FacturaQR factura) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(factura);
    }

    public static String getFecha(LocalDate fecha){
        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //
        return fecha.format(formatter);
    }
}

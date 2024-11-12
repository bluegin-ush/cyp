package blugin.com.ar.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SocioDTO{


    public Long id;

    public Long nro;

    public String nombre;

    public String apellido;
    public String tipoDoc;
    public Long numDoc;
    public String correo;

    public String telefono;

    public String domicilio;

    public EntidadCrediticiaDTO entidadCrediticia;

    public String tarjetaNum;

    public String tarjetaVto;

    public Boolean activo;

    public BigDecimal ctacte;

    public List<FacturaDTO> facturas;

    public SocioDTO(){
        this.facturas = new ArrayList<>();
    }

    public List<FacturaDTO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaDTO> facturas) {
        this.facturas = facturas;
    }

    public void addFactura(FacturaDTO factura){
        this.facturas.add(factura);
    }
}
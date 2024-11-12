package blugin.com.ar.dto;

public class EntidadCrediticiaDTO  {

    public Long id;
    public String tipo;
    public String nombre;
    public Boolean archivo;
    public Long cuit;
    public String contacto;

    @Override
    public String toString() {
        return "{ id=" + id +
                "tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", archivo=" + archivo +
                ", cuit=" + cuit +
                ", contacto='" + contacto + " }";
    }
}
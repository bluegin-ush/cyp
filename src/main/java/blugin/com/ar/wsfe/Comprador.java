
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Comprador complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="Comprador">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DocTipo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="DocNro" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="Porcentaje" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Comprador", propOrder = {
    "docTipo",
    "docNro",
    "porcentaje"
})
public class Comprador {

    @XmlElement(name = "DocTipo")
    protected int docTipo;
    @XmlElement(name = "DocNro")
    protected long docNro;
    @XmlElement(name = "Porcentaje")
    protected double porcentaje;

    /**
     * Obtiene el valor de la propiedad docTipo.
     * 
     */
    public int getDocTipo() {
        return docTipo;
    }

    /**
     * Define el valor de la propiedad docTipo.
     * 
     */
    public void setDocTipo(int value) {
        this.docTipo = value;
    }

    /**
     * Obtiene el valor de la propiedad docNro.
     * 
     */
    public long getDocNro() {
        return docNro;
    }

    /**
     * Define el valor de la propiedad docNro.
     * 
     */
    public void setDocNro(long value) {
        this.docNro = value;
    }

    /**
     * Obtiene el valor de la propiedad porcentaje.
     * 
     */
    public double getPorcentaje() {
        return porcentaje;
    }

    /**
     * Define el valor de la propiedad porcentaje.
     * 
     */
    public void setPorcentaje(double value) {
        this.porcentaje = value;
    }

}


package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Tributo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="Tributo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         <element name="Desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="BaseImp" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         <element name="Alic" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         <element name="Importe" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tributo", propOrder = {
    "id",
    "desc",
    "baseImp",
    "alic",
    "importe"
})
public class Tributo {

    @XmlElement(name = "Id")
    protected short id;
    @XmlElement(name = "Desc")
    protected String desc;
    @XmlElement(name = "BaseImp")
    protected double baseImp;
    @XmlElement(name = "Alic")
    protected double alic;
    @XmlElement(name = "Importe")
    protected double importe;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public short getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(short value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad desc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Define el valor de la propiedad desc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Obtiene el valor de la propiedad baseImp.
     * 
     */
    public double getBaseImp() {
        return baseImp;
    }

    /**
     * Define el valor de la propiedad baseImp.
     * 
     */
    public void setBaseImp(double value) {
        this.baseImp = value;
    }

    /**
     * Obtiene el valor de la propiedad alic.
     * 
     */
    public double getAlic() {
        return alic;
    }

    /**
     * Define el valor de la propiedad alic.
     * 
     */
    public void setAlic(double value) {
        this.alic = value;
    }

    /**
     * Obtiene el valor de la propiedad importe.
     * 
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Define el valor de la propiedad importe.
     * 
     */
    public void setImporte(double value) {
        this.importe = value;
    }

}

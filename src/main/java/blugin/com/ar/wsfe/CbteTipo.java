
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CbteTipo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="CbteTipo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="Desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="FchDesde" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="FchHasta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CbteTipo", propOrder = {
    "id",
    "desc",
    "fchDesde",
    "fchHasta"
})
public class CbteTipo {

    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "Desc")
    protected String desc;
    @XmlElement(name = "FchDesde")
    protected String fchDesde;
    @XmlElement(name = "FchHasta")
    protected String fchHasta;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(int value) {
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
     * Obtiene el valor de la propiedad fchDesde.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFchDesde() {
        return fchDesde;
    }

    /**
     * Define el valor de la propiedad fchDesde.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFchDesde(String value) {
        this.fchDesde = value;
    }

    /**
     * Obtiene el valor de la propiedad fchHasta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFchHasta() {
        return fchHasta;
    }

    /**
     * Define el valor de la propiedad fchHasta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFchHasta(String value) {
        this.fchHasta = value;
    }

}

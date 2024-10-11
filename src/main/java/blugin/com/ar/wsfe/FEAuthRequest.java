
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FEAuthRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="FEAuthRequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Cuit" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FEAuthRequest", propOrder = {
    "token",
    "sign",
    "cuit"
})
public class FEAuthRequest {

    @XmlElement(name = "Token")
    protected String token;
    @XmlElement(name = "Sign")
    protected String sign;
    @XmlElement(name = "Cuit")
    protected long cuit;

    /**
     * Obtiene el valor de la propiedad token.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Define el valor de la propiedad token.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Obtiene el valor de la propiedad sign.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSign() {
        return sign;
    }

    /**
     * Define el valor de la propiedad sign.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSign(String value) {
        this.sign = value;
    }

    /**
     * Obtiene el valor de la propiedad cuit.
     * 
     */
    public long getCuit() {
        return cuit;
    }

    /**
     * Define el valor de la propiedad cuit.
     * 
     */
    public void setCuit(long value) {
        this.cuit = value;
    }

}

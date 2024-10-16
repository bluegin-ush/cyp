
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Auth" type="{http://ar.gov.afip.dif.FEV1/}FEAuthRequest" minOccurs="0"/>
 *         <element name="Periodo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="Orden" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "auth",
    "periodo",
    "orden"
})
@XmlRootElement(name = "FECAEASolicitar")
public class FECAEASolicitar {

    @XmlElement(name = "Auth")
    protected FEAuthRequest auth;
    @XmlElement(name = "Periodo")
    protected int periodo;
    @XmlElement(name = "Orden")
    protected short orden;

    /**
     * Obtiene el valor de la propiedad auth.
     * 
     * @return
     *     possible object is
     *     {@link FEAuthRequest }
     *     
     */
    public FEAuthRequest getAuth() {
        return auth;
    }

    /**
     * Define el valor de la propiedad auth.
     * 
     * @param value
     *     allowed object is
     *     {@link FEAuthRequest }
     *     
     */
    public void setAuth(FEAuthRequest value) {
        this.auth = value;
    }

    /**
     * Obtiene el valor de la propiedad periodo.
     * 
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Define el valor de la propiedad periodo.
     * 
     */
    public void setPeriodo(int value) {
        this.periodo = value;
    }

    /**
     * Obtiene el valor de la propiedad orden.
     * 
     */
    public short getOrden() {
        return orden;
    }

    /**
     * Define el valor de la propiedad orden.
     * 
     */
    public void setOrden(short value) {
        this.orden = value;
    }

}

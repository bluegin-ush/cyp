
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.*;


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
 *         <element name="FeCAEARegInfReq" type="{http://ar.gov.afip.dif.FEV1/}FECAEARequest" minOccurs="0"/>
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
    "feCAEARegInfReq"
})
@XmlRootElement(name = "FECAEARegInformativo")
public class FECAEARegInformativo {

    @XmlElement(name = "Auth")
    protected FEAuthRequest auth;
    @XmlElement(name = "FeCAEARegInfReq")
    protected FECAEARequest feCAEARegInfReq;

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
     * Obtiene el valor de la propiedad feCAEARegInfReq.
     * 
     * @return
     *     possible object is
     *     {@link FECAEARequest }
     *     
     */
    public FECAEARequest getFeCAEARegInfReq() {
        return feCAEARegInfReq;
    }

    /**
     * Define el valor de la propiedad feCAEARegInfReq.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEARequest }
     *     
     */
    public void setFeCAEARegInfReq(FECAEARequest value) {
        this.feCAEARegInfReq = value;
    }

}


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
 *         <element name="FeCompConsReq" type="{http://ar.gov.afip.dif.FEV1/}FECompConsultaReq" minOccurs="0"/>
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
    "feCompConsReq"
})
@XmlRootElement(name = "FECompConsultar")
public class FECompConsultar {

    @XmlElement(name = "Auth")
    protected FEAuthRequest auth;
    @XmlElement(name = "FeCompConsReq")
    protected FECompConsultaReq feCompConsReq;

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
     * Obtiene el valor de la propiedad feCompConsReq.
     * 
     * @return
     *     possible object is
     *     {@link FECompConsultaReq }
     *     
     */
    public FECompConsultaReq getFeCompConsReq() {
        return feCompConsReq;
    }

    /**
     * Define el valor de la propiedad feCompConsReq.
     * 
     * @param value
     *     allowed object is
     *     {@link FECompConsultaReq }
     *     
     */
    public void setFeCompConsReq(FECompConsultaReq value) {
        this.feCompConsReq = value;
    }

}

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
 *         <element name="FECAEAConsultarResult" type="{http://ar.gov.afip.dif.FEV1/}FECAEAGetResponse" minOccurs="0"/>
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
    "fecaeaConsultarResult"
})
@XmlRootElement(name = "FECAEAConsultarResponse")
public class FECAEAConsultarResponse {

    @XmlElement(name = "FECAEAConsultarResult")
    protected FECAEAGetResponse fecaeaConsultarResult;

    /**
     * Obtiene el valor de la propiedad fecaeaConsultarResult.
     * 
     * @return
     *     possible object is
     *     {@link FECAEAGetResponse }
     *     
     */
    public FECAEAGetResponse getFECAEAConsultarResult() {
        return fecaeaConsultarResult;
    }

    /**
     * Define el valor de la propiedad fecaeaConsultarResult.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEAGetResponse }
     *     
     */
    public void setFECAEAConsultarResult(FECAEAGetResponse value) {
        this.fecaeaConsultarResult = value;
    }

}

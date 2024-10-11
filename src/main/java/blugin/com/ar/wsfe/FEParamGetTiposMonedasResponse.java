
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
 *         <element name="FEParamGetTiposMonedasResult" type="{http://ar.gov.afip.dif.FEV1/}MonedaResponse" minOccurs="0"/>
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
    "feParamGetTiposMonedasResult"
})
@XmlRootElement(name = "FEParamGetTiposMonedasResponse")
public class FEParamGetTiposMonedasResponse {

    @XmlElement(name = "FEParamGetTiposMonedasResult")
    protected MonedaResponse feParamGetTiposMonedasResult;

    /**
     * Obtiene el valor de la propiedad feParamGetTiposMonedasResult.
     * 
     * @return
     *     possible object is
     *     {@link MonedaResponse }
     *     
     */
    public MonedaResponse getFEParamGetTiposMonedasResult() {
        return feParamGetTiposMonedasResult;
    }

    /**
     * Define el valor de la propiedad feParamGetTiposMonedasResult.
     * 
     * @param value
     *     allowed object is
     *     {@link MonedaResponse }
     *     
     */
    public void setFEParamGetTiposMonedasResult(MonedaResponse value) {
        this.feParamGetTiposMonedasResult = value;
    }

}

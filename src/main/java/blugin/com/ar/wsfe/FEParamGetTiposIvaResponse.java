
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
 *         <element name="FEParamGetTiposIvaResult" type="{http://ar.gov.afip.dif.FEV1/}IvaTipoResponse" minOccurs="0"/>
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
    "feParamGetTiposIvaResult"
})
@XmlRootElement(name = "FEParamGetTiposIvaResponse")
public class FEParamGetTiposIvaResponse {

    @XmlElement(name = "FEParamGetTiposIvaResult")
    protected IvaTipoResponse feParamGetTiposIvaResult;

    /**
     * Obtiene el valor de la propiedad feParamGetTiposIvaResult.
     * 
     * @return
     *     possible object is
     *     {@link IvaTipoResponse }
     *     
     */
    public IvaTipoResponse getFEParamGetTiposIvaResult() {
        return feParamGetTiposIvaResult;
    }

    /**
     * Define el valor de la propiedad feParamGetTiposIvaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link IvaTipoResponse }
     *     
     */
    public void setFEParamGetTiposIvaResult(IvaTipoResponse value) {
        this.feParamGetTiposIvaResult = value;
    }

}

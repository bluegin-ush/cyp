
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
 *         <element name="FEParamGetPtosVentaResult" type="{http://ar.gov.afip.dif.FEV1/}FEPtoVentaResponse" minOccurs="0"/>
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
    "feParamGetPtosVentaResult"
})
@XmlRootElement(name = "FEParamGetPtosVentaResponse")
public class FEParamGetPtosVentaResponse {

    @XmlElement(name = "FEParamGetPtosVentaResult")
    protected FEPtoVentaResponse feParamGetPtosVentaResult;

    /**
     * Obtiene el valor de la propiedad feParamGetPtosVentaResult.
     * 
     * @return
     *     possible object is
     *     {@link FEPtoVentaResponse }
     *     
     */
    public FEPtoVentaResponse getFEParamGetPtosVentaResult() {
        return feParamGetPtosVentaResult;
    }

    /**
     * Define el valor de la propiedad feParamGetPtosVentaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link FEPtoVentaResponse }
     *     
     */
    public void setFEParamGetPtosVentaResult(FEPtoVentaResponse value) {
        this.feParamGetPtosVentaResult = value;
    }

}

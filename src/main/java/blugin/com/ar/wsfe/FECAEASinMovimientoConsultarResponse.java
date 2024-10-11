
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
 *         <element name="FECAEASinMovimientoConsultarResult" type="{http://ar.gov.afip.dif.FEV1/}FECAEASinMovConsResponse" minOccurs="0"/>
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
    "fecaeaSinMovimientoConsultarResult"
})
@XmlRootElement(name = "FECAEASinMovimientoConsultarResponse")
public class FECAEASinMovimientoConsultarResponse {

    @XmlElement(name = "FECAEASinMovimientoConsultarResult")
    protected FECAEASinMovConsResponse fecaeaSinMovimientoConsultarResult;

    /**
     * Obtiene el valor de la propiedad fecaeaSinMovimientoConsultarResult.
     * 
     * @return
     *     possible object is
     *     {@link FECAEASinMovConsResponse }
     *     
     */
    public FECAEASinMovConsResponse getFECAEASinMovimientoConsultarResult() {
        return fecaeaSinMovimientoConsultarResult;
    }

    /**
     * Define el valor de la propiedad fecaeaSinMovimientoConsultarResult.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEASinMovConsResponse }
     *     
     */
    public void setFECAEASinMovimientoConsultarResult(FECAEASinMovConsResponse value) {
        this.fecaeaSinMovimientoConsultarResult = value;
    }

}

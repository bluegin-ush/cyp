
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
 *         <element name="FEParamGetTiposPaisesResult" type="{http://ar.gov.afip.dif.FEV1/}FEPaisResponse" minOccurs="0"/>
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
    "feParamGetTiposPaisesResult"
})
@XmlRootElement(name = "FEParamGetTiposPaisesResponse")
public class FEParamGetTiposPaisesResponse {

    @XmlElement(name = "FEParamGetTiposPaisesResult")
    protected FEPaisResponse feParamGetTiposPaisesResult;

    /**
     * Obtiene el valor de la propiedad feParamGetTiposPaisesResult.
     * 
     * @return
     *     possible object is
     *     {@link FEPaisResponse }
     *     
     */
    public FEPaisResponse getFEParamGetTiposPaisesResult() {
        return feParamGetTiposPaisesResult;
    }

    /**
     * Define el valor de la propiedad feParamGetTiposPaisesResult.
     * 
     * @param value
     *     allowed object is
     *     {@link FEPaisResponse }
     *     
     */
    public void setFEParamGetTiposPaisesResult(FEPaisResponse value) {
        this.feParamGetTiposPaisesResult = value;
    }

}

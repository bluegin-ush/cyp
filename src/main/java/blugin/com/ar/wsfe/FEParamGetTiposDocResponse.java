
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
 *         <element name="FEParamGetTiposDocResult" type="{http://ar.gov.afip.dif.FEV1/}DocTipoResponse" minOccurs="0"/>
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
    "feParamGetTiposDocResult"
})
@XmlRootElement(name = "FEParamGetTiposDocResponse")
public class FEParamGetTiposDocResponse {

    @XmlElement(name = "FEParamGetTiposDocResult")
    protected DocTipoResponse feParamGetTiposDocResult;

    /**
     * Obtiene el valor de la propiedad feParamGetTiposDocResult.
     * 
     * @return
     *     possible object is
     *     {@link DocTipoResponse }
     *     
     */
    public DocTipoResponse getFEParamGetTiposDocResult() {
        return feParamGetTiposDocResult;
    }

    /**
     * Define el valor de la propiedad feParamGetTiposDocResult.
     * 
     * @param value
     *     allowed object is
     *     {@link DocTipoResponse }
     *     
     */
    public void setFEParamGetTiposDocResult(DocTipoResponse value) {
        this.feParamGetTiposDocResult = value;
    }

}

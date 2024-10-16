
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
 *         <element name="FECAEARegInformativoResult" type="{http://ar.gov.afip.dif.FEV1/}FECAEAResponse" minOccurs="0"/>
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
    "fecaeaRegInformativoResult"
})
@XmlRootElement(name = "FECAEARegInformativoResponse")
public class FECAEARegInformativoResponse {

    @XmlElement(name = "FECAEARegInformativoResult")
    protected FECAEAResponse fecaeaRegInformativoResult;

    /**
     * Obtiene el valor de la propiedad fecaeaRegInformativoResult.
     * 
     * @return
     *     possible object is
     *     {@link FECAEAResponse }
     *     
     */
    public FECAEAResponse getFECAEARegInformativoResult() {
        return fecaeaRegInformativoResult;
    }

    /**
     * Define el valor de la propiedad fecaeaRegInformativoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEAResponse }
     *     
     */
    public void setFECAEARegInformativoResult(FECAEAResponse value) {
        this.fecaeaRegInformativoResult = value;
    }

}


package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FECAERequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="FECAERequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="FeCabReq" type="{http://ar.gov.afip.dif.FEV1/}FECAECabRequest" minOccurs="0"/>
 *         <element name="FeDetReq" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfFECAEDetRequest" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FECAERequest", propOrder = {
    "feCabReq",
    "feDetReq"
})
public class FECAERequest {

    @XmlElement(name = "FeCabReq")
    protected FECAECabRequest feCabReq;
    @XmlElement(name = "FeDetReq")
    protected ArrayOfFECAEDetRequest feDetReq;

    /**
     * Obtiene el valor de la propiedad feCabReq.
     * 
     * @return
     *     possible object is
     *     {@link FECAECabRequest }
     *     
     */
    public FECAECabRequest getFeCabReq() {
        return feCabReq;
    }

    /**
     * Define el valor de la propiedad feCabReq.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAECabRequest }
     *     
     */
    public void setFeCabReq(FECAECabRequest value) {
        this.feCabReq = value;
    }

    /**
     * Obtiene el valor de la propiedad feDetReq.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFECAEDetRequest }
     *     
     */
    public ArrayOfFECAEDetRequest getFeDetReq() {
        return feDetReq;
    }

    /**
     * Define el valor de la propiedad feDetReq.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFECAEDetRequest }
     *     
     */
    public void setFeDetReq(ArrayOfFECAEDetRequest value) {
        this.feDetReq = value;
    }

}

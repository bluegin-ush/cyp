
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FECAEARequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="FECAEARequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="FeCabReq" type="{http://ar.gov.afip.dif.FEV1/}FECAEACabRequest" minOccurs="0"/>
 *         <element name="FeDetReq" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfFECAEADetRequest" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FECAEARequest", propOrder = {
    "feCabReq",
    "feDetReq"
})
public class FECAEARequest {

    @XmlElement(name = "FeCabReq")
    protected FECAEACabRequest feCabReq;
    @XmlElement(name = "FeDetReq")
    protected ArrayOfFECAEADetRequest feDetReq;

    /**
     * Obtiene el valor de la propiedad feCabReq.
     * 
     * @return
     *     possible object is
     *     {@link FECAEACabRequest }
     *     
     */
    public FECAEACabRequest getFeCabReq() {
        return feCabReq;
    }

    /**
     * Define el valor de la propiedad feCabReq.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEACabRequest }
     *     
     */
    public void setFeCabReq(FECAEACabRequest value) {
        this.feCabReq = value;
    }

    /**
     * Obtiene el valor de la propiedad feDetReq.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFECAEADetRequest }
     *     
     */
    public ArrayOfFECAEADetRequest getFeDetReq() {
        return feDetReq;
    }

    /**
     * Define el valor de la propiedad feDetReq.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFECAEADetRequest }
     *     
     */
    public void setFeDetReq(ArrayOfFECAEADetRequest value) {
        this.feDetReq = value;
    }

}

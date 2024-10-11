
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
 *         <element name="FEDummyResult" type="{http://ar.gov.afip.dif.FEV1/}DummyResponse" minOccurs="0"/>
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
    "feDummyResult"
})
@XmlRootElement(name = "FEDummyResponse")
public class FEDummyResponse {

    @XmlElement(name = "FEDummyResult")
    protected DummyResponse feDummyResult;

    /**
     * Obtiene el valor de la propiedad feDummyResult.
     * 
     * @return
     *     possible object is
     *     {@link DummyResponse }
     *     
     */
    public DummyResponse getFEDummyResult() {
        return feDummyResult;
    }

    /**
     * Define el valor de la propiedad feDummyResult.
     * 
     * @param value
     *     allowed object is
     *     {@link DummyResponse }
     *     
     */
    public void setFEDummyResult(DummyResponse value) {
        this.feDummyResult = value;
    }

}

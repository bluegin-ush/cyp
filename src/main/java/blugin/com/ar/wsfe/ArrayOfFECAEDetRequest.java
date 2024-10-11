
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ArrayOfFECAEDetRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfFECAEDetRequest">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="FECAEDetRequest" type="{http://ar.gov.afip.dif.FEV1/}FECAEDetRequest" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFECAEDetRequest", propOrder = {
    "fecaeDetRequest"
})
public class ArrayOfFECAEDetRequest {

    @XmlElement(name = "FECAEDetRequest", nillable = true)
    protected List<FECAEDetRequest> fecaeDetRequest;

    /**
     * Gets the value of the fecaeDetRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the fecaeDetRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFECAEDetRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FECAEDetRequest }
     * 
     * 
     * @return
     *     The value of the fecaeDetRequest property.
     */
    public List<FECAEDetRequest> getFECAEDetRequest() {
        if (fecaeDetRequest == null) {
            fecaeDetRequest = new ArrayList<>();
        }
        return this.fecaeDetRequest;
    }

}


package blugin.com.ar.wsfe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfObs complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfObs">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Obs" type="{http://ar.gov.afip.dif.FEV1/}Obs" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfObs", propOrder = {
    "obs"
})
public class ArrayOfObs {

    @XmlElement(name = "Obs", nillable = true)
    protected List<Obs> obs;

    /**
     * Gets the value of the obs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the obs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Obs }
     * 
     * 
     * @return
     *     The value of the obs property.
     */
    public List<Obs> getObs() {
        if (obs == null) {
            obs = new ArrayList<>();
        }
        return this.obs;
    }

}

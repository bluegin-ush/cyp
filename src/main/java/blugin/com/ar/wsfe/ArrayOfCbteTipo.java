
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ArrayOfCbteTipo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfCbteTipo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="CbteTipo" type="{http://ar.gov.afip.dif.FEV1/}CbteTipo" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCbteTipo", propOrder = {
    "cbteTipo"
})
public class ArrayOfCbteTipo {

    @XmlElement(name = "CbteTipo", nillable = true)
    protected List<CbteTipo> cbteTipo;

    /**
     * Gets the value of the cbteTipo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the cbteTipo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCbteTipo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CbteTipo }
     * 
     * 
     * @return
     *     The value of the cbteTipo property.
     */
    public List<CbteTipo> getCbteTipo() {
        if (cbteTipo == null) {
            cbteTipo = new ArrayList<>();
        }
        return this.cbteTipo;
    }

}

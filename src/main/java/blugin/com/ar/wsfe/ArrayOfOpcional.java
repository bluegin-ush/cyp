
package blugin.com.ar.wsfe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfOpcional complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfOpcional">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Opcional" type="{http://ar.gov.afip.dif.FEV1/}Opcional" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOpcional", propOrder = {
    "opcional"
})
public class ArrayOfOpcional {

    @XmlElement(name = "Opcional", nillable = true)
    protected List<Opcional> opcional;

    /**
     * Gets the value of the opcional property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the opcional property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOpcional().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Opcional }
     * 
     * 
     * @return
     *     The value of the opcional property.
     */
    public List<Opcional> getOpcional() {
        if (opcional == null) {
            opcional = new ArrayList<>();
        }
        return this.opcional;
    }

}


package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ArrayOfComprador complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfComprador">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Comprador" type="{http://ar.gov.afip.dif.FEV1/}Comprador" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfComprador", propOrder = {
    "comprador"
})
public class ArrayOfComprador {

    @XmlElement(name = "Comprador", nillable = true)
    protected List<Comprador> comprador;

    /**
     * Gets the value of the comprador property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the comprador property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComprador().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comprador }
     * 
     * 
     * @return
     *     The value of the comprador property.
     */
    public List<Comprador> getComprador() {
        if (comprador == null) {
            comprador = new ArrayList<>();
        }
        return this.comprador;
    }

}

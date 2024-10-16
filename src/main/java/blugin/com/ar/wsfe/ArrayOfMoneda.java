
package blugin.com.ar.wsfe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfMoneda complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfMoneda">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Moneda" type="{http://ar.gov.afip.dif.FEV1/}Moneda" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMoneda", propOrder = {
    "moneda"
})
public class ArrayOfMoneda {

    @XmlElement(name = "Moneda", nillable = true)
    protected List<Moneda> moneda;

    /**
     * Gets the value of the moneda property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the moneda property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMoneda().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Moneda }
     * 
     * 
     * @return
     *     The value of the moneda property.
     */
    public List<Moneda> getMoneda() {
        if (moneda == null) {
            moneda = new ArrayList<>();
        }
        return this.moneda;
    }

}

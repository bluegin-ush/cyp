
package blugin.com.ar.wsfe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfPtoVenta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfPtoVenta">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PtoVenta" type="{http://ar.gov.afip.dif.FEV1/}PtoVenta" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPtoVenta", propOrder = {
    "ptoVenta"
})
public class ArrayOfPtoVenta {

    @XmlElement(name = "PtoVenta", nillable = true)
    protected List<PtoVenta> ptoVenta;

    /**
     * Gets the value of the ptoVenta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the ptoVenta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPtoVenta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PtoVenta }
     * 
     * 
     * @return
     *     The value of the ptoVenta property.
     */
    public List<PtoVenta> getPtoVenta() {
        if (ptoVenta == null) {
            ptoVenta = new ArrayList<>();
        }
        return this.ptoVenta;
    }

}

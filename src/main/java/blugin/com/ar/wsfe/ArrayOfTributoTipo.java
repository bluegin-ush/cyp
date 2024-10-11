
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Clase Java para ArrayOfTributoTipo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ArrayOfTributoTipo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="TributoTipo" type="{http://ar.gov.afip.dif.FEV1/}TributoTipo" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTributoTipo", propOrder = {
    "tributoTipo"
})
public class ArrayOfTributoTipo {

    @XmlElement(name = "TributoTipo", nillable = true)
    protected List<TributoTipo> tributoTipo;

    /**
     * Gets the value of the tributoTipo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the tributoTipo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTributoTipo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TributoTipo }
     * 
     * 
     * @return
     *     The value of the tributoTipo property.
     */
    public List<TributoTipo> getTributoTipo() {
        if (tributoTipo == null) {
            tributoTipo = new ArrayList<>();
        }
        return this.tributoTipo;
    }

}


package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para OpcionalTipoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="OpcionalTipoResponse">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="ResultGet" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfOpcionalTipo" minOccurs="0"/>
 *         <element name="Errors" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfErr" minOccurs="0"/>
 *         <element name="Events" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfEvt" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpcionalTipoResponse", propOrder = {
    "resultGet",
    "errors",
    "events"
})
public class OpcionalTipoResponse {

    @XmlElement(name = "ResultGet")
    protected ArrayOfOpcionalTipo resultGet;
    @XmlElement(name = "Errors")
    protected ArrayOfErr errors;
    @XmlElement(name = "Events")
    protected ArrayOfEvt events;

    /**
     * Obtiene el valor de la propiedad resultGet.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOpcionalTipo }
     *     
     */
    public ArrayOfOpcionalTipo getResultGet() {
        return resultGet;
    }

    /**
     * Define el valor de la propiedad resultGet.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOpcionalTipo }
     *     
     */
    public void setResultGet(ArrayOfOpcionalTipo value) {
        this.resultGet = value;
    }

    /**
     * Obtiene el valor de la propiedad errors.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfErr }
     *     
     */
    public ArrayOfErr getErrors() {
        return errors;
    }

    /**
     * Define el valor de la propiedad errors.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfErr }
     *     
     */
    public void setErrors(ArrayOfErr value) {
        this.errors = value;
    }

    /**
     * Obtiene el valor de la propiedad events.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEvt }
     *     
     */
    public ArrayOfEvt getEvents() {
        return events;
    }

    /**
     * Define el valor de la propiedad events.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEvt }
     *     
     */
    public void setEvents(ArrayOfEvt value) {
        this.events = value;
    }

}

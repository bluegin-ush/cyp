
package blugin.com.ar.wsfe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para FECAEAGet complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="FECAEAGet">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="CAEA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Periodo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="Orden" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         <element name="FchVigDesde" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="FchVigHasta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="FchTopeInf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="FchProceso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Observaciones" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfObs" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FECAEAGet", propOrder = {
    "caea",
    "periodo",
    "orden",
    "fchVigDesde",
    "fchVigHasta",
    "fchTopeInf",
    "fchProceso",
    "observaciones"
})
public class FECAEAGet {

    @XmlElement(name = "CAEA")
    protected String caea;
    @XmlElement(name = "Periodo")
    protected int periodo;
    @XmlElement(name = "Orden")
    protected short orden;
    @XmlElement(name = "FchVigDesde")
    protected String fchVigDesde;
    @XmlElement(name = "FchVigHasta")
    protected String fchVigHasta;
    @XmlElement(name = "FchTopeInf")
    protected String fchTopeInf;
    @XmlElement(name = "FchProceso")
    protected String fchProceso;
    @XmlElement(name = "Observaciones")
    protected ArrayOfObs observaciones;

    /**
     * Obtiene el valor de la propiedad caea.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAEA() {
        return caea;
    }

    /**
     * Define el valor de la propiedad caea.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAEA(String value) {
        this.caea = value;
    }

    /**
     * Obtiene el valor de la propiedad periodo.
     * 
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Define el valor de la propiedad periodo.
     * 
     */
    public void setPeriodo(int value) {
        this.periodo = value;
    }

    /**
     * Obtiene el valor de la propiedad orden.
     * 
     */
    public short getOrden() {
        return orden;
    }

    /**
     * Define el valor de la propiedad orden.
     * 
     */
    public void setOrden(short value) {
        this.orden = value;
    }

    /**
     * Obtiene el valor de la propiedad fchVigDesde.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFchVigDesde() {
        return fchVigDesde;
    }

    /**
     * Define el valor de la propiedad fchVigDesde.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFchVigDesde(String value) {
        this.fchVigDesde = value;
    }

    /**
     * Obtiene el valor de la propiedad fchVigHasta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFchVigHasta() {
        return fchVigHasta;
    }

    /**
     * Define el valor de la propiedad fchVigHasta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFchVigHasta(String value) {
        this.fchVigHasta = value;
    }

    /**
     * Obtiene el valor de la propiedad fchTopeInf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFchTopeInf() {
        return fchTopeInf;
    }

    /**
     * Define el valor de la propiedad fchTopeInf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFchTopeInf(String value) {
        this.fchTopeInf = value;
    }

    /**
     * Obtiene el valor de la propiedad fchProceso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFchProceso() {
        return fchProceso;
    }

    /**
     * Define el valor de la propiedad fchProceso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFchProceso(String value) {
        this.fchProceso = value;
    }

    /**
     * Obtiene el valor de la propiedad observaciones.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfObs }
     *     
     */
    public ArrayOfObs getObservaciones() {
        return observaciones;
    }

    /**
     * Define el valor de la propiedad observaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfObs }
     *     
     */
    public void setObservaciones(ArrayOfObs value) {
        this.observaciones = value;
    }

}

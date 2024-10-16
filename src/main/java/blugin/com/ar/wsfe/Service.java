
package blugin.com.ar.wsfe;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;


/**
 * Web Service orientado  al  servicio  de Facturacion electronica RG2485 V1
 * 
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebServiceClient(name = "Service", targetNamespace = "http://ar.gov.afip.dif.FEV1/", wsdlLocation = "https://wswhomo.afip.gov.ar/wsfev1/service.asmx?WSDL")
public class Service
    extends jakarta.xml.ws.Service
{

    private static final URL SERVICE_WSDL_LOCATION;
    private static final WebServiceException SERVICE_EXCEPTION;
    private static final QName SERVICE_QNAME = new QName("http://ar.gov.afip.dif.FEV1/", "Service");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://wswhomo.afip.gov.ar/wsfev1/service.asmx?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SERVICE_WSDL_LOCATION = url;
        SERVICE_EXCEPTION = e;
    }

    public Service() {
        super(__getWsdlLocation(), SERVICE_QNAME);
    }

    public Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), SERVICE_QNAME, features);
    }

    public Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE_QNAME);
    }

    public Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SERVICE_QNAME, features);
    }

    public Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ServiceSoap
     */
    @WebEndpoint(name = "ServiceSoap")
    public ServiceSoap getServiceSoap() {
        return super.getPort(new QName("http://ar.gov.afip.dif.FEV1/", "ServiceSoap"), ServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServiceSoap
     */
    @WebEndpoint(name = "ServiceSoap")
    public ServiceSoap getServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://ar.gov.afip.dif.FEV1/", "ServiceSoap"), ServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SERVICE_EXCEPTION!= null) {
            throw SERVICE_EXCEPTION;
        }
        return SERVICE_WSDL_LOCATION;
    }

}

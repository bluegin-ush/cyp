package blugin.com.ar.service;

import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.Person;
import blugin.com.ar.dto.FacturaDTO;
import blugin.com.ar.fe.*;
import blugin.com.ar.repository.PersonRepository;
import blugin.com.ar.wsfe.FEAuthRequest;
import blugin.com.ar.wsfe.wrappers.Comprobante;
import blugin.com.ar.wsfe.wrappers.Persona;
import blugin.com.ar.wsfe.wrappers.TiposComprobante;
import blugin.com.ar.wsfe.wrappers.TiposDocumento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FacturaService {

    private static final Logger log = LoggerFactory.getLogger(FacturaService.class);

    String endpoint = "https://wsaahomo.afip.gov.ar/ws/services/LoginCms";
    String certPath = "certificados/afip-cyp.pem";
    String keyPath  = "certificados/clave-prueba";
    String service  = "wsfe";

    //12hs
    Duration threshold = Duration.ofHours(12);
    Duration expiration = Duration.ofHours(12);

    //optional
    String dn = "SERIALNUMBER=CUIT 20290833869, CN=cyp";

    //
    long cuit     = 20290833869L; // CUIT del emisor

    //
    int puntoDeVenta = 1;

    /**
     *
     * @param factura
     * @return
     * @throws Exception
     */
    public Factura facturar(Factura factura) throws Exception {

        FEAuthRequest autorizacion = obtenerAutorizacion();

        int nroComprobanteSiguiente = WSFEClient.ultimoComprobante(autorizacion, puntoDeVenta, TiposComprobante.FACTURA_C.codigo()) + 1;

        Comprobante comprobante = new Comprobante(puntoDeVenta, nroComprobanteSiguiente, TiposComprobante.FACTURA_C);

        Persona persona = new Persona(TiposDocumento.getTipo(factura.socio.tipoDoc), factura.socio.numDoc);

        //
        FacturaDatosAFIP facturaDatosAFIP = WSFEClient.emitirFactura(autorizacion, comprobante, persona, factura.fecha.toLocalDate(), factura.total);

        factura.nroComprobante = nroComprobanteSiguiente;
        factura.cae = facturaDatosAFIP.cae;
        factura.vtoCae = facturaDatosAFIP.getVto();

        return factura;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    private FEAuthRequest obtenerAutorizacion() throws Exception {
        //
        Map authTokens = new HashMap();

        //
        AuthTokenAndSign auth = new AuthTokenAndSign();

        if(auth.isThresholdExceeded(threshold)) {
            //
            String ltr = SignXML.getLoginTicketRequest(null, service, expiration);

            //
            String ltrs = SignXML.getLoginTicketRequestSigned(certPath, keyPath, ltr);

            //
            try{
                //
                authTokens = WSAAClient.getTicketAuthorization(endpoint, ltrs);

            }catch(SOAPFaultException e){

                // again with sames credentials
                authTokens.put("token", auth.getToken());
                authTokens.put("sign", auth.getSign());

                e.printStackTrace();
            }

            //
            auth.saveAuthToken(authTokens);

        }

        //
        String token    = auth.getToken();
        String sign     = auth.getSign();

        System.out.println("Ticket de Acceso: " + token);
        System.out.println("Firma de Acceso: " + sign);

        //WSFEClient.ping();

        return WSFEClient.obtenerAutorizacion(token, sign, cuit);

    }
}
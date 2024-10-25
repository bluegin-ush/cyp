package blugin.com.ar.fe;

import jakarta.xml.ws.soap.SOAPFaultException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            //test
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
            Map authTokens = new HashMap();

            //
            AuthTokenAndSign_deprecated auth = new AuthTokenAndSign_deprecated();

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

            //
            long cuit     = 20290833869L; // CUIT del emisor

            //
            WSFEClient.ping();

            //
            //WSFEClient.tiposDoc(token, sign, cuit);

            //
            //WSFEClient.tiposComprobante(token, sign, cuit);

            //
            //WSFEClient.tiposConceptos(token, sign, cuit);

            //
            //WSFEClient.tiposMonedas(token, sign, cuit);

            //
            //WSFEClient.ultimoComprobante(token, sign, cuit, TiposComprobante.FACTURA_C.codigo());

            //WSFEClient.puntosDeVenta(token, sign, cuit);

            //
//             FEAuthRequest autorizacion = WSFEClient.obtenerAutorizacion(token, sign, cuit);

            //
            //WSFEClient.emitirFacturaEjemplo(autorizacion);

            //
//            Comprobante comprobante = new Comprobante(1, 10, TiposComprobante.NOTA_CREDITO_C);

            //
//            Persona persona = new Persona(TiposDocumento.DNI, 29141313);

            //
//            LocalDate fecha = LocalDate.now();

            //
//            BigDecimal importe = new BigDecimal(40000);

            //
            /*
                asociacion.setCuit();
                asociacion.setPtoVta();
                asociacion.setTipo();
                asociacion.setNro();
                asociacion.setCbteFch();
             */

//            WSFEClient.cancelarFactura(autorizacion, comprobante, persona, fecha, importe);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

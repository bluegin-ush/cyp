package blugin.com.ar.service;

import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.NotaDeCredito;
import blugin.com.ar.dto.FacturaQR;
import blugin.com.ar.fe.*;
import blugin.com.ar.repository.ConfiguracionRepository;
import blugin.com.ar.wsfe.CbteAsoc;
import blugin.com.ar.wsfe.FEAuthRequest;
import blugin.com.ar.wsfe.wrappers.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
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

    // CUIT del emisor
    long cuitEmisor = 20290833869L;

    //
    int puntoDeVenta = 1;

    @Inject
    ConfiguracionRepository configuracionRepository;

    @Inject
    AuthTokenAndSign authTokenAndSign;

    public FacturaService(){

        System.out.println("Cargando valores por defecto!!");

    }

    public void cargarConfiguraciones(){

        System.out.println("============== DocumentBuilderFactory configurado a Xerces ==============");
        DocumentBuilderFactory doc = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        System.out.println("============== DocumentBuilderFactory instnaciado a "+ doc.getClass().getName()+"==============");
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();

            SOAPMessage soapMessage = messageFactory.createMessage();

            System.out.println("============== Implementación de SOAPMessage: " + soapMessage.getClass().getName() + "==============");

        } catch (SOAPException e) {
            System.out.println("========= ERROR ===== "+e.getMessage());;
        }

        // Imprimir la implementación utilizada
        System.out.println("valores:");
        Map<String,String> configuraciones = configuracionRepository.obtenerTodasLasConfiguraciones();

        String modo = configuraciones.get("modo");

        System.out.printf("configuraciones cargadas a partir del modo: %s\n",modo);
        configuraciones.forEach((clave, valor) -> {
            if(clave.contains(modo)) {
                String claveModo = clave.split("-")[1];
                System.out.println("Clave: " + claveModo + " Valor: " + valor);
                switch (claveModo) {
                    case "endpoint" -> endpoint = valor;
                    case "certPath" -> certPath = valor;
                    case "cuitEmisor" -> cuitEmisor = Long.parseLong(valor);
                    case "dn" -> dn = valor;
                    case "puntoDeVenta" -> puntoDeVenta = Integer.parseInt(valor);
                    case "service" -> service = valor;
                    case "keyPath" -> keyPath = valor;
                    case "expiration" -> expiration = Duration.ofHours(Long.parseLong(valor));
                    case "threshold" -> threshold = Duration.ofHours(Long.parseLong(valor));
                }
            }
        });
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String ping() throws Exception {

        //cargarConfiguraciones();

        return WSFEClient.ping();
    }
    /**
     *
     * @param factura
     * @return
     * @throws Exception
     */
    public Factura facturar(Factura factura) throws Exception {

        cargarConfiguraciones();

        FEAuthRequest autorizacion = obtenerAutorizacion();

        long nroComprobanteSiguiente = WSFEClient.ultimoComprobante(autorizacion, puntoDeVenta, TiposComprobante.FACTURA_C.codigo()) + 1;

        Comprobante comprobante = new Comprobante(puntoDeVenta, nroComprobanteSiguiente, TiposComprobante.FACTURA_C);

        Persona persona = new Persona(TiposDocumento.getTipo(factura.socio.tipoDoc), factura.socio.numDoc);

        //no necesito asociar nada a la factura
        CbteAsoc asociacion = null;

        //
        FacturaDatosAFIP facturaDatosAFIP = WSFEClient.emitirFactura(autorizacion, comprobante, persona, factura.fecha.toLocalDate(), factura.total, asociacion);

        factura.nroComprobante = nroComprobanteSiguiente;
        factura.cae = facturaDatosAFIP.cae;
        factura.vtoCae = FacturaDatosAFIP.getVto(facturaDatosAFIP.vto);

        factura.qr = generarQR(factura, persona);

        return factura;
    }

    private String generarQR(Factura factura, Persona persona) throws IOException {
        FacturaQR qr = new FacturaQR(
                1,
                FacturaQR.getFecha(factura.fecha.toLocalDate()),
                cuitEmisor,
                puntoDeVenta,
                TiposComprobante.FACTURA_C.codigo(),
                factura.nroComprobante,
                factura.total,
                Monedas.PESOS_ARGENTINOS.codigo(),
                1,
                persona.tipo().codigo(),
                persona.numero(),
                "E",
                Long.parseLong(factura.cae));

        String url = "https://www.afip.gob.ar/fe/qr/";
        String json = FacturaQR.convertirAFacturaJSON(qr);

        // Convertir el JSON String a bytes
        byte[] jsonBytes = json.getBytes();

        // Codificar en Base64
        String base64Encoded = Base64.getEncoder().encodeToString(jsonBytes);

        //
        return String.format("%s?p=%s",url,base64Encoded);
    }

    public NotaDeCredito cancelar(NotaDeCredito notaDeCredito) throws Exception {

        cargarConfiguraciones();

        FEAuthRequest autorizacion = obtenerAutorizacion();

        long nroComprobanteSiguiente = WSFEClient.ultimoComprobante(autorizacion, puntoDeVenta, TiposComprobante.NOTA_CREDITO_C.codigo()) + 1;

        Comprobante comprobante = new Comprobante(puntoDeVenta, nroComprobanteSiguiente, TiposComprobante.NOTA_CREDITO_C);

        Persona persona = new Persona(TiposDocumento.getTipo(notaDeCredito.factura.socio.tipoDoc), notaDeCredito.factura.socio.numDoc);

        CbteAsoc asociacion = new CbteAsoc();
        asociacion.setCuit(String.valueOf(cuitEmisor));
        asociacion.setPtoVta(puntoDeVenta);
        asociacion.setTipo(TiposComprobante.FACTURA_C.codigo());
        asociacion.setNro(notaDeCredito.factura.nroComprobante);
        asociacion.setCbteFch(FacturaDatosAFIP.getVto(notaDeCredito.factura.vtoCae));

        //
        FacturaDatosAFIP facturaDatosAFIP = WSFEClient.emitirFactura(autorizacion, comprobante, persona, LocalDate.now(), notaDeCredito.total, asociacion);

        notaDeCredito.nroComprobante = nroComprobanteSiguiente;
        notaDeCredito.cae = facturaDatosAFIP.cae;
        notaDeCredito.vtoCae = FacturaDatosAFIP.getVto(facturaDatosAFIP.vto);

        return notaDeCredito;
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

        if(authTokenAndSign.isThresholdExceeded(threshold)) {
            //
            String ltr = SignXML.getLoginTicketRequest(null, service, expiration);

            //
            String ltrs = SignXML.getLoginTicketRequestSigned(certPath, keyPath, ltr);

            //
            try{
                //
                authTokens = WSAAClient.getTicketAuthorization(endpoint, ltrs);

                //
                authTokenAndSign.saveAuthToken(authTokens);

            }catch(SOAPFaultException e){

                // again with sames credentials
                log.error("Error al obtener los datos vía SOAP, intentamos establecer",e);

                //
                authTokenAndSign.setTimeStamp("0");

                /*try{
                    authTokens.put("token", authTokenAndSign.getToken());
                    authTokens.put("sign", authTokenAndSign.getSign());
                }catch (Exception eInPut){
                    log.error("En establecer las preferencias");
                    e.printStackTrace();
                }
                */
                e.printStackTrace();
            }


        }

        //
        String token    = authTokenAndSign.getToken();
        String sign     = authTokenAndSign.getSign();

        System.out.println("Ticket de Acceso: " + token);
        System.out.println("Firma de Acceso: " + sign);

        //WSFEClient.ping();
        if(token==null){
            throw new Exception("El token está nulo");
        }
        return WSFEClient.obtenerAuthRequest(token, sign, cuitEmisor);

    }
}
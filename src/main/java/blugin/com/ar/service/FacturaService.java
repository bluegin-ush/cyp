package blugin.com.ar.service;

import blugin.com.ar.cyp.model.Factura;
import blugin.com.ar.cyp.model.NotaDeCredito;
import blugin.com.ar.fe.*;
import blugin.com.ar.repository.ConfiguracionRepository;
import blugin.com.ar.wsfe.CbteAsoc;
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
import java.time.LocalDate;
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

    public FacturaService(){

        System.out.println("Cargando valores por defecto!!");

    }

    public void cargarConfiguraciones(){
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

        return factura;
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

        return WSFEClient.obtenerAuthRequest(token, sign, cuitEmisor);

    }
}
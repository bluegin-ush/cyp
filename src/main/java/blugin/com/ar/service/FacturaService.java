package blugin.com.ar.service;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.dto.FacturaQR;
import blugin.com.ar.fe.*;
import blugin.com.ar.repository.ConfiguracionRepository;
import blugin.com.ar.repository.FacturaRepository;
import blugin.com.ar.repository.LoteFacturaRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

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

    @Inject
    LoteFacturaRepository loteFacturaRepository;

    @Inject
    FacturaRepository facturaRepository;

    public FacturaService(){

        //System.out.println("Cargando valores por defecto!!");

    }

    public void cargarConfiguraciones(){

        Map<String,String> configuraciones = configuracionRepository.obtenerTodasLasConfiguraciones();

        String modo = configuraciones.get("modo");

        //System.out.printf("configuraciones cargadas a partir del modo: %s\n",modo);
        configuraciones.forEach((clave, valor) -> {
            if(clave.contains(modo)) {
                String claveModo = clave.split("-")[1];
                //System.out.println("Clave: " + claveModo + " Valor: " + valor);
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
    private FEAuthRequest autorizacion = null;

    public Factura facturar(Factura factura) throws Exception {

        if(volverACargarConfiguraciones() || autorizacion == null || (authTokenAndSign.isThresholdExceeded(threshold))){
            log.info("CARGANDO CONFIGURACIONES");
            cargarConfiguraciones();
            log.info("ESTABECIENDO AUTORIZACION");
            autorizacion = obtenerAutorizacion();
        }

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

    private boolean volverACargarConfiguraciones() {

        Map<String,String> configuraciones = configuracionRepository.obtenerTodasLasConfiguraciones();

        String recargar = configuraciones.get("recargar-configuraciones");

        return Boolean.valueOf(recargar);
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

        if(volverACargarConfiguraciones()){
            cargarConfiguraciones();
        }


        FEAuthRequest autorizacion = obtenerAutorizacion();

        long nroComprobanteSiguiente = WSFEClient.ultimoComprobante(autorizacion, puntoDeVenta, TiposComprobante.NOTA_CREDITO_C.codigo()) + 1;

        Comprobante comprobante = new Comprobante(puntoDeVenta, nroComprobanteSiguiente, TiposComprobante.NOTA_CREDITO_C);

        Persona persona = new Persona(TiposDocumento.getTipo(notaDeCredito.factura.socio.tipoDoc), notaDeCredito.factura.socio.numDoc);

        CbteAsoc asociacion = new CbteAsoc();
        asociacion.setCuit(String.valueOf(cuitEmisor));
        asociacion.setPtoVta(puntoDeVenta);
        asociacion.setTipo(TiposComprobante.FACTURA_C.codigo());
        asociacion.setNro(notaDeCredito.factura.nroComprobante);
        asociacion.setCbteFch(FacturaDatosAFIP.getVto(notaDeCredito.factura.fecha.toLocalDate()));

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
                if(e.getFault().getFaultCode().equals("ns1:coe.alreadyAuthenticated")) {
                    authTokenAndSign.setTime(""+(Instant.now().plus(Duration.ofMinutes(10))).toEpochMilli());
                    //authTokenAndSign.saveAuthToken(authTokens);
                }

                e.printStackTrace();
            }


        }

        //
        String token    = authTokenAndSign.getToken();
        String sign     = authTokenAndSign.getSign();

        //System.out.println("Ticket de Acceso: " + token);
        //System.out.println("Firma de Acceso: " + sign);

        //WSFEClient.ping();
        if(token==null){
            throw new Exception("El token está nulo");
        }
        return WSFEClient.obtenerAuthRequest(token, sign, cuitEmisor);

    }

    //@Asynchronous

    public void facturarEnLote(LoteFactura lote) {

        int i=0;
        boolean seProdujoError=false;

        //

        for(Factura factura: lote.facturas) {

            try {
                // Emitimos lafactura
                log.info(String.format("Emitiendo la factura del socio [%s]",factura.socio));
                facturar(factura);

                //
                factura.estado = EstadoFactura.EMITIDA; // Estado inicial

                //
                lote.idFacturasEmitidas.add(factura.id);

                // Actualizar progreso
                lote.progreso = ((lote.idFacturasEmitidas.size() + lote.idFacturasErroneas.size() ) / lote.facturas.size())*100;
                loteFacturaRepository.persist(lote);

            } catch (Exception e) {

                //registramos el error
                log.error(String.format("Al generar la factura %s - msg: %s", factura.id, e.getMessage()));
                lote.idFacturasErroneas.add(factura.id);

                seProdujoError=true;
            }

        }



        // Marcar lote como completado
        if(seProdujoError){
            lote.estado = EstadoLote.FALLIDO;
        }else{
            lote.estado = EstadoLote.COMPLETADO;
        }
        loteFacturaRepository.persist(lote);

    }



    //@Asynchronous
    public void preFacturar(LoteFactura lote, List<Socio> sociosActivos) {

        for (Socio socio : sociosActivos) {

            BigDecimal totalFactura = BigDecimal.ZERO;
            List<Item> items = new ArrayList<>();

            // Obtener los servicios asociados al socio
            for (Servicio servicio : socio.servicios) {
                Item item = new Item();
                item.concepto = servicio.descripcion;
                item.cantidad = 1;
                item.precio = servicio.costo;
                items.add(item);

                // Sumar el costo del servicio al total de la factura
                totalFactura = totalFactura.add(servicio.costo);
            }

            //verificamos que tenga una factura con servicios y monto mayor a 0
            if(totalFactura.compareTo(BigDecimal.ZERO)>0) {

                // Crear una nueva factura
                Factura factura = new Factura();
                factura.fecha = LocalDateTime.now();
                factura.socio = socio;
                factura.tipo = Factura.Tipo.C; // Factura tipo C por defecto
                factura.items = items;
                factura.total = totalFactura;
                factura.estado = EstadoFactura.PRE_EMITADA; // Estado inicial

                //actualizamos el estado de la ctacte del socio
                factura.socio.ctacte = factura.socio.ctacte.add(totalFactura);

                // Relacionar los items con la factura
                for (Item item : items) {
                    item.factura = factura;
                }

                lote.agregarFactura(factura);

                facturaRepository.persist(factura);
            }
        }

        //
        lote.estado   = EstadoLote.EN_PROCESO;
        loteFacturaRepository.persist(lote);

    }

}
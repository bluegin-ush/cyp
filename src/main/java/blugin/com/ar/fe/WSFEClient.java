package blugin.com.ar.fe;

import blugin.com.ar.wsfe.*;
import blugin.com.ar.wsfe.wrappers.*;
import blugin.com.ar.wsfe.wrappers.Tributo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

public class WSFEClient {

    public static FEAuthRequest obtenerAuthRequest(String token, String sign, long cuit) {

        FEAuthRequest auth = new FEAuthRequest();
        auth.setToken(token);
        auth.setSign(sign);
        auth.setCuit(cuit);

        return auth;
    }

    public static FacturaDatosAFIP emitirFactura(FEAuthRequest auth,
                                                        Comprobante comprobante,
                                                        Persona persona,
                                                        LocalDate fecha,
                                                        BigDecimal importe,
                                                        CbteAsoc asociacion) throws Exception {

        System.out.println("Comenzado proceso de emisión...");
        FacturaDatosAFIP facturaDatosAFIP = new FacturaDatosAFIP();

        Tributos otrosTributos = new Tributos();

        FECAERequest caeReq = requestSinIVA(Conceptos.SERVICIOS,
                comprobante,
                persona,
                fecha,
                importe,
                otrosTributos,
                asociacion);

        ServiceSoap service = new Service().getServiceSoap();

        FECAEResponse response = service.fecaeSolicitar(auth, caeReq);

        ArrayOfErr errores = response.getErrors();
        Optional<Exception> errorTecnico =
                (errores != null && errores.getErr() != null && !errores.getErr().isEmpty())
                ? Optional.of(new Exception())
                : Optional.empty();

        if (errorTecnico.isPresent()) {
            System.out.printf("ERROR: %s\n",errorTecnico.get().getMessage());
            throw errorTecnico.get();
        }

        Optional<Exception> errorFuncional =
                ("R".equals(response.getFeDetResp().getFECAEDetResponse().get(0).getResultado()))
                ? Optional.of(new Exception(response.getFeDetResp().getFECAEDetResponse().get(0).getObservaciones().getObs().get(0).getMsg()))
                : Optional.empty();
        if (errorFuncional.isPresent()) {
            System.out.printf("ERROR: %s\n",errorFuncional.get().getMessage());
            throw errorFuncional.get();
        }

        System.out.printf("CbteTipo: %s - Cuit: %s - CantReg: %s - Reproceso: %s - PtoVta: %s - FchProceso: %s - Resultado: %s\n",
                response.getFeCabResp().getCbteTipo(),
                response.getFeCabResp().getCuit(),
                response.getFeCabResp().getCantReg(),
                response.getFeCabResp().getReproceso(),
                response.getFeCabResp().getPtoVta(),
                response.getFeCabResp().getFchProceso(),
                response.getFeCabResp().getResultado());

        //Resultado = A=APROBADO, R=RECHAZADO, P=PARCIAL

        for(FECAEDetResponse detalle: response.getFeDetResp().getFECAEDetResponse()){
            System.out.printf("CAE: %s - FchVto: %s - Resultado: %s - DocTipo: %s - DocNro: %s - CbteFch: %s - CbteDesde: %s - CbteHasta: %s - Concepto: %s\n",
                    detalle.getCAE(),
                    detalle.getCAEFchVto(),
                    detalle.getResultado(),
                    detalle.getDocTipo(),
                    detalle.getDocNro(),
                    detalle.getCbteFch(),
                    detalle.getCbteDesde(),
                    detalle.getCbteHasta(),
                    detalle.getConcepto());
        }

        facturaDatosAFIP.cae = response.getFeDetResp().getFECAEDetResponse().get(0).getCAE();
        facturaDatosAFIP.vto = response.getFeDetResp().getFECAEDetResponse().get(0).getCAEFchVto();

        return facturaDatosAFIP;
    }

    private static FECAERequest requestSinIVA(Conceptos concepto, Comprobante comprobante, Persona persona,
                                              LocalDate fecha, BigDecimal importe, Tributos otrosTributos, CbteAsoc asociacion) {
        final FECAEDetRequest detalle = new FECAEDetRequest();

        detalle.setConcepto(concepto.codigo());
        detalle.setCbteDesde(comprobante.numero());
        detalle.setCbteHasta(comprobante.numero());
        detalle.setDocTipo(persona.tipo().codigo());
        detalle.setDocNro(persona.numero());
        detalle.setCbteFch(DateTimeFormatter.ofPattern("yyyyMMdd").format(fecha));
        BigDecimal total = importe.add(otrosTributos.total());
        detalle.setImpTotal(Numeros.doubleConPrecision(total));

        //detalle.setImpTotConc(Numeros.doubleConPrecision(total));//para C debe ser 0
        detalle.setImpTotConc(0d);

        detalle.setImpNeto(Numeros.doubleConPrecision(importe));
        //detalle.setImpNeto(0d);para B es 0
        detalle.setImpOpEx(0d);
        //detalle.setImpTrib(Numeros.doubleConPrecision(otrosTributos.total()));
        detalle.setImpTrib(0d);
        detalle.setImpIVA(0);
        detalle.setMonId(Monedas.PESOS_ARGENTINOS.codigo());
        detalle.setMonCotiz(1d);
        detalle.setFchServDesde(DateTimeFormatter.ofPattern("yyyyMMdd").format(fecha));
        detalle.setFchServHasta(DateTimeFormatter.ofPattern("yyyyMMdd").format(fecha));
        detalle.setFchVtoPago(DateTimeFormatter.ofPattern("yyyyMMdd").format(fecha));
        ponerTributosSiTiene(otrosTributos, detalle);

        final ArrayOfFECAEDetRequest detalles = new ArrayOfFECAEDetRequest();

        if(asociacion != null ) {
            /* AGREGAR UNA ASOCIACION (ej nota crédito o débito) */

            ArrayOfCbteAsoc asociaciones = new ArrayOfCbteAsoc();

            /*CbteAsoc asociacion = new CbteAsoc();
            asociacion.setCuit();
            asociacion.setPtoVta();
            asociacion.setTipo();
            asociacion.setNro();
            asociacion.setCbteFch();
            */
            asociaciones.getCbteAsoc().add(asociacion);
            detalle.setCbtesAsoc(asociaciones);



        }
        detalles.getFECAEDetRequest().add(detalle);

        final FECAERequest request = new FECAERequest();
        request.setFeCabReq(cabecera(comprobante.puntoVenta(), comprobante.tipo()));
        request.setFeDetReq(detalles);

        /*System.out.printf("total: %s - suma: %s\n",
                detalle.getImpTotal(),
                (detalle.getImpTotConc()+
                        detalle.getImpNeto()+
                        detalle.getImpOpEx()+
                        detalle.getImpTrib()+
                        detalle.getImpIVA()));*/

        return request;
    }

    private static void ponerTributosSiTiene(Tributos tributos, FECAEDetRequest detalle) {

        //
        ArrayOfTributo array = tributos(tributos);

        if (!array.getTributo().isEmpty()) {
            detalle.setTributos(array);
        }
    }

    private static ArrayOfTributo tributos(Tributos tributos) {

        //
        final ArrayOfTributo array = new ArrayOfTributo();

        //
        tributos.todos().stream().map(Tributo::aTributo).forEach((tributo) -> array.getTributo().add(tributo));

        //
        return array;
    }

    private static FECAECabRequest cabecera(int puntoVenta, TiposComprobante tipo) {

        //
        final FECAECabRequest cabecera = new FECAECabRequest();

        //
        cabecera.setCantReg(1);
        cabecera.setPtoVta(puntoVenta);
        cabecera.setCbteTipo(tipo.codigo());

        //
        return cabecera;
    }

    public static int ultimoNroComprobante(FEAuthRequest auth, int tipo) {

        //
        ServiceSoap service = new Service().getServiceSoap();
        FERecuperaLastCbteResponse comprobante = service.feCompUltimoAutorizado(auth, Comprobante.puntoVentaDefault(), tipo);

        //
        return comprobante.getCbteNro();

    }
    //================================================================================//
    //================================================================================//
    //================================================================================//

    public static String ping() {
        ServiceSoap service = new Service().getServiceSoap();

        DummyResponse dummy = service.feDummy();
        return String.format("Ping: appServer: %s, authServer: %s, dbServer: %s\n",
                dummy.getAppServer(),
                dummy.getAuthServer(),
                dummy.getDbServer());
    }

    public static void tiposDoc(String token, String sign, long cuit) {

        FEAuthRequest auth = new FEAuthRequest();
        auth.setToken(token);
        auth.setSign(sign);
        auth.setCuit(cuit);

        ServiceSoap service = new Service().getServiceSoap();
        DocTipoResponse tipo = service.feParamGetTiposDoc(auth);

        for(DocTipo doc : tipo.getResultGet().getDocTipo()){
            System.out.printf("%s - %s - %s - %s\n",doc.getId(), doc.getDesc(), doc.getFchDesde(), doc.getFchHasta());
        }
    }

    public static void tiposComprobante(String token, String sign, long cuit) {

        FEAuthRequest auth = new FEAuthRequest();
        auth.setToken(token);
        auth.setSign(sign);
        auth.setCuit(cuit);

        ServiceSoap service = new Service().getServiceSoap();
        CbteTipoResponse tipo = service.feParamGetTiposCbte(auth);

        for(CbteTipo cbte : tipo.getResultGet().getCbteTipo()){
            System.out.printf("%s - %s - %s - %s\n",cbte.getId(), cbte.getDesc(), cbte.getFchDesde(), cbte.getFchHasta());
        }
    }

    public static void tiposConceptos(String token, String sign, long cuit) {

        FEAuthRequest auth = new FEAuthRequest();
        auth.setToken(token);
        auth.setSign(sign);
        auth.setCuit(cuit);

        ServiceSoap service = new Service().getServiceSoap();
        ConceptoTipoResponse tipo = service.feParamGetTiposConcepto(auth);

        for(ConceptoTipo concepto : tipo.getResultGet().getConceptoTipo()){
            System.out.printf("%s - %s - %s - %s\n",concepto.getId(), concepto.getDesc(), concepto.getFchDesde(), concepto.getFchHasta());
        }
    }

    public static void tiposMonedas(String token, String sign, long cuit) {

        FEAuthRequest auth = new FEAuthRequest();
        auth.setToken(token);
        auth.setSign(sign);
        auth.setCuit(cuit);

        ServiceSoap service = new Service().getServiceSoap();
        MonedaResponse mon = service.feParamGetTiposMonedas(auth);

        for(Moneda moneda : mon.getResultGet().getMoneda()){
            System.out.printf("%s - %s - %s - %s\n",moneda.getId(), moneda.getDesc(), moneda.getFchDesde(), moneda.getFchHasta());
        }
    }

    public static void ultimoComprobante(String token, String sign, long cuit, int tipo) {

        FEAuthRequest auth = new FEAuthRequest();
        auth.setToken(token);
        auth.setSign(sign);
        auth.setCuit(cuit);

        ServiceSoap service = new Service().getServiceSoap();
        FERecuperaLastCbteResponse comprobante = service.feCompUltimoAutorizado(auth, Comprobante.puntoVentaDefault(), tipo);

        System.out.printf("%s - %s \n",comprobante.getCbteTipo(), comprobante.getCbteNro());

    }

    /**
     *
     * @param auth
     * @param puntoDeVenta
     * @param tipo
     * @return
     */
    public static int ultimoComprobante(FEAuthRequest auth, int puntoDeVenta, int tipo) {

        //
        ServiceSoap service = new Service().getServiceSoap();

        //
        FERecuperaLastCbteResponse comprobante = service.feCompUltimoAutorizado(auth, puntoDeVenta, tipo);

        System.out.printf("Obteniendo último comprobante: tipo:%s - nro:%s \n",comprobante.getCbteTipo(), comprobante.getCbteNro());

        return comprobante.getCbteNro();
    }


    public static void puntosDeVenta(String token, String sign, long cuit) {

        final int ERROR_SIN_RESULTADOS = 602;

        FEAuthRequest auth = new FEAuthRequest();
        auth.setToken(token);
        auth.setSign(sign);
        auth.setCuit(cuit);

        ServiceSoap service = new Service().getServiceSoap();
        FEPtoVentaResponse respuesta = service.feParamGetPtosVenta(auth);
        Optional<Err> error = respuesta.getErrors().getErr().stream().findFirst();
        if (error.isPresent()) {
            if (error.get().getCode() == ERROR_SIN_RESULTADOS) {
                System.out.println("No hay puntos de ventas establecidos");
            }
            throw new RuntimeException(formatearErrores(respuesta.getErrors()));
        }else
            for(PtoVenta ptoVenta : respuesta.getResultGet().getPtoVenta()){
                System.out.printf("%s - %s - %s - %s\n",ptoVenta.getBloqueado(), ptoVenta.getNro(), ptoVenta.getEmisionTipo(), ptoVenta.getFchBaja());
            }
    }

    private static String formatearErrores(ArrayOfErr errores) {
        return errores
                .getErr()
                .stream()
                .map((e) -> String.format("[AFIP-%d] %s", e.getCode(), e.getMsg()))
                .collect(Collectors.joining("; ", "Errores:", ""));
    }


}

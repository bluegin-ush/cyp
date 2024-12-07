package blugin.com.ar.service;

import blugin.com.ar.cyp.model.Archivo;
import blugin.com.ar.cyp.model.EntidadCrediticia;
import blugin.com.ar.cyp.model.Factura;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ArchivoService {

    String NUMERO_ENTIDAD_STR = "0014161855";
    Long NUMERO_ENTIDAD_LONG = Long.parseLong(NUMERO_ENTIDAD_STR);

    public String generarContenidoArchivo(Archivo archivo) {

        // generar el contenido del archivo según el tipo de entidad
        if (archivo.entidadCrediticia.id == 1){
            //"visa"
            return generarContenidoArchivoVISA(archivo);

        } else if (archivo.entidadCrediticia.id == 2) {
            //"master"
            return generarContenidoArchivoMASTER(archivo);
        } else {
            //Otro no implementado
            throw new RuntimeException(String.format("No existe implementación para la entidad %s",archivo.entidadCrediticia.nombre));
        }

    }


    private String generarContenidoArchivoVISA(Archivo archivo) {

        // Generar el archivo en formato texto
        StringBuilder archivoTexto = new StringBuilder();



        // CABECERA
        // Tipo de registro
        archivoTexto.append("0");
        // Constante
        archivoTexto.append("DEBLIQC ");
        // Número del Establecimiento que generó el archivo
        //archivoTexto.append(String.format("%010d", NUMERO_ENTIDAD_LONG));
        archivoTexto.append(NUMERO_ENTIDAD_STR);
        // Constante
        archivoTexto.append("900000    ");
        // Fecha de generación del archivo (AAAAMMDD)
        archivoTexto.append(archivo.fechaGeneracion.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        // Hora de generación del archivo (HHMM)
        archivoTexto.append(archivo.fechaGeneracion.format(DateTimeFormatter.ofPattern("HHmm")));
        // Tipo de Archivo. Débitos a liquidar
        archivoTexto.append("0");
        // Estado archivo - Constante espacios
        archivoTexto.append(String.format("%2s",""));
        // Reservado - Constante espacios
        archivoTexto.append(String.format("%55s",""));
        // Marca de fin de registro
        archivoTexto.append("*").append("\n");

        // DETALLE
        // Obtener la lista de facturas del archivo
        for (Factura factura : archivo.facturas) {
            // Tipo de registro
            archivoTexto.append("1");
            // Número de Tarjeta
            archivoTexto.append(String.format("%16s", factura.socio.tarjetaNum));
            // Reservado - Constante 3 espacios
            archivoTexto.append(String.format("%3s",""));
            // Referencia o número de comprobante o Nro. Secuencial ascendente único por archivo
            archivoTexto.append(String.format("%08d", factura.nroComprobante));
            // Fecha de origen o vencimiento del débito Formato AAAAMMDD
            archivoTexto.append(factura.fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            // Transacción - Constante 0005
            archivoTexto.append("0005");
            // Importe
            archivoTexto.append(String.format("%015d", factura.getSaldo().multiply(new BigDecimal("100")).longValue()));
            // Identificador del débito
            //TODO: mejorar por un valor del tipo secuencia.
            //IDENTIFICADOR DEL DEBITO
            archivoTexto.append(String.format("%015d", factura.id));
            // Código de alta de Identificador
            archivoTexto.append("E");
            // Estado archivo - Constante espacios
            archivoTexto.append(String.format("%2s",""));
            // Reservado - Constante espacios
            archivoTexto.append(String.format("%26s",""));
            // Marca de fin de registro
            archivoTexto.append("*").append("\n");
        }

        // PIE
        // Tipo de registro
        archivoTexto.append("9");
        // Constante
        archivoTexto.append("DEBLIQD ");
        // Nro. de Establecimiento que recibe el archivo
        //archivoTexto.append(String.format("%010d", NUMERO_ENTIDAD));
        archivoTexto.append(NUMERO_ENTIDAD_STR);
        // Constante
        archivoTexto.append("900000    ");
        // Fecha de generación del archivo (AAAAMMDD)
        archivoTexto.append(archivo.fechaGeneracion.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        // Hora de generación del archivo (HHMM)
        archivoTexto.append(archivo.fechaGeneracion.format(DateTimeFormatter.ofPattern("HHmm")));
        // Cantidad total registros detalle
        archivoTexto.append(String.format("%07d", archivo.facturas.size()));
        // Sumatoria Importes registros detalle
        archivoTexto.append(String.format("%015d", archivo.importeTotal.multiply(new BigDecimal("100")).longValue()));
        // Reservado - Constante espacios
        archivoTexto.append(String.format("%36s",""));

        // Marca de fin de registro
        archivoTexto.append("*");

        return archivoTexto.toString();
    }

    private String generarContenidoArchivoMASTER(Archivo archivo) {

        return "NOT-IMPLEMENTED";
    }

    public String procesarArchivo(EntidadCrediticia entidad, List<String> lineasArchivo) {

        // generar el contenido del archivo según el tipo de entidad
        if (entidad.id == 1){
            //"visa"
            return procesarArchivoVisa(lineasArchivo);

        } else if (entidad.id == 2) {
            //"master"
            return procesarArchivoMaster(lineasArchivo);

        } else {
            //Otro no implementado
            throw new RuntimeException(String.format("No existe implementación para procesar la entidad %s",entidad.nombre));
        }

    }

    private String procesarArchivoMaster(List<String> lineasArchivo) {
        return "NOT-IMPLEMENTED";
    }

    private String procesarArchivoVisa(List<String> lineasArchivo) {
        StringBuilder salida = new StringBuilder();

        salida.append("PRIMERA LINEA --->").append(lineasArchivo.get(0)).append("\n");
        String linea = lineasArchivo.get(0);

        // Extraer la información de la cabecera
        //1 Registro Cabecera Rendición de Débitos Automáticos X 1 300 -->
        //2 Constante '0' X 1 1
        String c2 = linea.substring(0, 1);
        //3 Archivo (' RDEBLIQC ' =Salida) X 2 8
        String c3 = linea.substring(1, 9);
        //4 Constante '900000' X 10 10
        String c4 = linea.substring(9, 19);
        //5 Nro. de Establecimiento que recibe  el archivo  X 20 10
        String c5 = linea.substring(19, 29);
        //6 Fecha de Origen (ssaammdd) N 30 8
        String c6 = linea.substring(29, 37);
        //7 Hora de Origen (hhmm) N 38 4
        String c7 = linea.substring(37, 41);
        //8 Constante ' ' X 42 1
        String c8 = linea.substring(41, 42);
        //9 Constante ' ' X 43 2
        String c9 = linea.substring(42, 44);
        //10 Libre X 45 255
        String c10 = linea.substring(44, 299);
        //11 Cosntante '*' X 300 1
        String c11 = linea.substring(299, 300);

        // Mostrar la información en pantalla
        System.out.println("C2: " + c2);
        System.out.println("C3: " + c3);
        System.out.println("C4: " + c4);
        System.out.println("C5: " + c5);
        System.out.println("C6: " + c6);
        System.out.println("C7: " + c7);
        System.out.println("C8: " + c8);
        System.out.println("C9: " + c9);
        System.out.println("C10: " + c10);
        System.out.println("C11: " + c11);

        BigDecimal sumaImportes = BigDecimal.ZERO;
        int aceptadas = 0;
        int rechazadas = 0;
        int noSonMias = 0;

        Boolean esConsumo = Boolean.FALSE;
        Boolean esMiEstablecimiento = Boolean.FALSE;
        BigDecimal importe =BigDecimal.ZERO;
        Long facturaId = 0L;
        Boolean esAceptada = Boolean.FALSE;

        for (int i=1; i<lineasArchivo.size()-1; i++){
            salida.append("MEDIAS LINEA --->").append(lineasArchivo.get(i)).append("\n");
            linea = lineasArchivo.get(i);
            // Extraer la información de los movimientos
            //1 Registro Detalle de Rendición de Débitos Automáticos X 1 300
            //2 Constante '1' X 1 1
            String m2 = linea.substring(0, 1);
            //3 Datos de Rendición X 2 249 -->
            //4 Datos del Movimiento X 2 128 -->
            //5 Código de Banco X 2 3
            String m5 = linea.substring(1, 4);
            //6 Código de Casa X 5 3
            String m6 = linea.substring(4, 8);
            //7 Número de Lote X 8 4
            String m7 = linea.substring(7, 11);
            //8 Código de Transacción: X 12 4
            //	0005: Consumos en Pesos
            //	6000: Devolución
            String m8 = linea.substring(11, 15);
           esConsumo = m8.equals("0005");

            //9 Uso reservado X 16 1
            String m9 = linea.substring(15, 16);
            //10 Número de Establecimiento X 17 10
            String m10 = linea.substring(16, 26);
           esMiEstablecimiento = m10.equals(NUMERO_ENTIDAD_STR);

            //11 Número de Tarjeta X 27 16
            String m11 = linea.substring(26, 42);
            //12 Número de Cupón X 43 8 Para establecimientos de seguros (rubro 6300)
            String m12 = linea.substring(42, 50);
            //13 Fecha de Origen DDMMAA) X 51 6
            String m13 = linea.substring(50, 56);
            //14 Uso reservado X 57 6
            String m14 = linea.substring(56, 62);
            //15 Importe SN 63 15 2
            String m15 = linea.substring(62, 77);
           importe = BigDecimal.valueOf(Double.valueOf(m15)/100);

            //16 Cuotas X 78 2
            String m16 = linea.substring(77, 79);
            //17 Uso reservado X 80 15
            String m17 = linea.substring(79, 94);
            //18 Identificador X 95 15
            // IDENTIFICADOR DEL DEBITO - factura.id
            String m18 = linea.substring(94, 109);
           facturaId = Long.parseLong(m18);

            //19 Marca Primer Débito X 110 1
            String m19 = linea.substring(109, 110);
            //20 Número de Cuenta X 111 10
            String m20 = linea.substring(110, 120);
            //21 Código de rama del tipo de seguro X 121 3
            String m21 = linea.substring(120, 123);
            //22 Endoso de la póliza (cuando corresponde) X 124 3
            String m22 = linea.substring(123, 126);
            //23 Uso Reservado X 127 3
            String m23 = linea.substring(126, 129);
            //24 Datos Operativos X 130 171 -->
            //25 Estado del Movimiento X 130 1
            String m25 = linea.substring(129, 130);
            //26 Rechazo Código Motivo 1 N 131 2
            String m26 = linea.substring(130, 132);
           esAceptada = m26.equals("00");

            //27 Rechazo Descripción Código Motivo 1 X 133 29
            String m27 = linea.substring(132, 161);
            //28 Rechazo Código Motivo 2 N 162 2
            String m28 = linea.substring(161, 163);
            //29 Rechazo Descripción Código Motivo 2 X 164 29
            String m29 = linea.substring(163, 192);
            //30 Uso reservado N 193 16
            String m30 = linea.substring(192, 208);
            //31 Número de Tarjeta Nueva N 209 16
            String m31 = linea.substring(208, 224);
            //32 Fecha de Presentación (DDMMAA) X 225 6
            String m32 = linea.substring(224, 230);
            //33 Fecha de Pago X 231 6
            String m33 = linea.substring(230, 236);
            //34 Cartera X 237 2
            String m34 = linea.substring(236, 238);
            //35 Uso reservado X 239 12
            String m35 = linea.substring(238, 250);
            //36 Libre X 251 49
            String m36 = linea.substring(250, 299);
            //37 Cosntante '*' X 300 1
            String m37 = linea.substring(299, 300);

            // Mostrar la información en pantalla
            /*System.out.println("m2: " + m2);
            System.out.println("m5: " + m5);
            System.out.println("m6: " + m6);
            System.out.println("m7: " + m7);
            System.out.println("m8: " + m8);
            System.out.println("m9: " + m9);
            System.out.println("m10: " + m10);
            System.out.println("m11: " + m11);
            System.out.println("m12: " + m12);
            System.out.println("m13: " + m13);
            System.out.println("m14: " + m14);
            System.out.println("m15: " + m15);
            System.out.println("m16: " + m16);
            System.out.println("m17: " + m17);
            System.out.println("m18: " + m18);
            System.out.println("m19: " + m19);
            System.out.println("m20: " + m20);
            System.out.println("m21: " + m21);
            System.out.println("m22: " + m22);
            System.out.println("m23: " + m23);
            System.out.println("m25: " + m25);
            System.out.println("m26: " + m26);
            System.out.println("m27: " + m27);
            System.out.println("m28: " + m28);
            System.out.println("m29: " + m29);
            System.out.println("m30: " + m30);
            System.out.println("m31: " + m31);
            System.out.println("m32: " + m32);
            System.out.println("m33: " + m33);
            System.out.println("m34: " + m34);
            System.out.println("m35: " + m35);
            System.out.println("m36: " + m36);
            System.out.println("m37: " + m37);*/

            /*Boolean esConsumo
            Boolean esMiEstablecimiento
            BigDecimal importe
            Long facturaId
            Boolean esAceptada*/

            if (esMiEstablecimiento){
                System.out.println("IDENTIFICACION="+facturaId);
                if(esAceptada){
                    aceptadas++;
                    sumaImportes = sumaImportes.add(importe);
                }else {
                    rechazadas++;
                }
            }else{
                noSonMias++;
            }
        }

        salida.append("ULTIMA LINEA --->").append(lineasArchivo.get(lineasArchivo.size()-1));
        // Extraer la información del pie
        linea = lineasArchivo.get(lineasArchivo.size()-1);
        //1 Registro Cabecera Rendición de Débitos Automáticos X 1 300
        //2 Cosntante '9' X 1 1
        String p2 = linea.substring(0, 1);
        //3 Archivo (' RDEBLIQC ' =Salida) X 2 8
        String p3 = linea.substring(1, 9);
        //4 Constante '90000' X 10 10
        String p4 = linea.substring(9, 19);
        //5 Nro. de Establecimiento que recibe el archivo X 20 10
        String p5 = linea.substring(19, 29);
        //6 Fecha de Origen (ssaammdd) N 30 8
        String p6 = linea.substring(29, 37);
        //7 Hora de Origen (hhmm) N 38 4
        String p7 = linea.substring(37, 41);
        //8 Cantidad de Registros N 42 7
        String p8 = linea.substring(41, 48);

        //9 Importe Total N 49 15 2
        String p9 = linea.substring(48, 63);
       BigDecimal importeTotal = BigDecimal.valueOf(Double.valueOf(p9)/100);

        //10 Libre X 64 236
        String p10 = linea.substring(63, 299);
        //11 Cosntante '*' X 300 1
        String p11 = linea.substring(299, 300);

        // Mostrar la información en pantalla
        /*System.out.println("p2: " + p2);
        System.out.println("p3: " + p3);
        System.out.println("p4: " + p4);
        System.out.println("p5: " + p5);
        System.out.println("p6: " + p6);
        System.out.println("p7: " + p7);
        System.out.println("p8: " + p8);
        System.out.println("p9: " + p9);
        System.out.println("p10: " + p10);
        System.out.println("p11: " + p11);*/

        System.out.println("IMPORTE TOTAL= "+importeTotal);
        System.out.println("IMPORTEs proc= "+sumaImportes);
        System.out.println("RECHAZADAS   = "+rechazadas);
        System.out.println("ACEPTADAS    = "+aceptadas);
        System.out.println("DESCARTADAS  = "+noSonMias);
        System.out.println("CANT-REGIST. = "+p8);

        return salida.toString();

    }

}
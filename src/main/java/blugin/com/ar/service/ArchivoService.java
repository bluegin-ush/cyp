package blugin.com.ar.service;

import blugin.com.ar.cyp.model.Archivo;
import blugin.com.ar.cyp.model.Factura;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ArchivoService {


    public void procesarArchivoVisa(Archivo archivo, String contenido, StringBuilder detalleErrores) {

        //
        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            // Procesar cada línea y detectar errores
            //detalleErrores.append("un error");
        }
    }

    public void procesarArchivoMaster(Archivo archivo, String contenido, StringBuilder detalleErrores) {
        //
        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            // Procesar cada línea y detectar errores
            //detalleErrores.append("un error");
        }
    }

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

        long NUMERO_ENTIDAD = Long.parseLong("0014161855");

        // CABECERA
        // Tipo de registro
        archivoTexto.append("0");
        // Constante
        archivoTexto.append("DEBLIQC ");
        // Número del Establecimiento que generó el archivo
        archivoTexto.append(String.format("%010d", NUMERO_ENTIDAD));
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
        archivoTexto.append(String.format("%010d", NUMERO_ENTIDAD));
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
        return "";
    }

}
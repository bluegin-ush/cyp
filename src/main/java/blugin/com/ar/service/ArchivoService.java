package blugin.com.ar.service;

import blugin.com.ar.cyp.model.Archivo;
import jakarta.enterprise.context.ApplicationScoped;

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

}
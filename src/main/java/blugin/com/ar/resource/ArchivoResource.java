package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.ArchivoRepository;
import blugin.com.ar.service.ArchivoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@ResourceProperties(paged = false)
@Path("/archivo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class ArchivoResource { //extends PanacheRepositoryResource<ArchivoProcesamientoRepository, ArchivoProcesamiento, Long> { }

    @Inject
    ArchivoRepository archivoRepository;

    //@Inject
    //PagoService pagoService;

    @POST
    @Path("/generar/{entidadId}")
    public Response generarArchivo(@PathParam("entidadId") Long entidadId, List<Long> facturasIds) {

        // Buscar la entidad crediticia
        EntidadCrediticia entidadCrediticia = EntidadCrediticia.findById(entidadId);
        if (entidadCrediticia == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Entidad crediticia no encontrada").build();
        }

        // Buscar las facturas
        List<Factura> facturas = Factura.list("id in ?1 and socio.entidadCrediticia.id = ?2 and estado = ?3",
                facturasIds, entidadId, EstadoFactura.EMITIDA);
        if (facturas.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se encontraron facturas emitidas para la entidad crediticia en la lista proporcionada.")
                    .build();
        }

        // Generar el archivo en formato texto
        StringBuilder archivoTexto = new StringBuilder();
        archivoTexto.append("Archivo de facturas para la entidad: ").append(entidadCrediticia.nombre).append("\n");
        archivoTexto.append("Fecha de generación: ").append(LocalDateTime.now()).append("\n\n");
        archivoTexto.append("Facturas:\n");
        for (Factura factura : facturas) {
            archivoTexto.append("Factura ID: ").append(factura.id)
                    .append(" | Socio: ").append(factura.socio.nombre).append(" ").append(factura.socio.apellido)
                    .append(" | Total: ").append(factura.total).append("\n");
        }

        // Crear el archivo en la entidad Archivo
        Archivo archivo = new Archivo();
        archivo.entidadCrediticia = entidadCrediticia;
        archivo.fechaGeneracion = LocalDateTime.now();
        archivo.estado = EstadoArchivo.GENERADO;
        archivo.archivo = archivoTexto.toString();  // Guardar el archivo en formato texto
        archivo.facturas = facturas;

        // Persistir el archivo
        archivo.persist();

        return Response.ok(archivo).build();
    }

    //TODO filtrar por periodo
    @GET
    public Response listarArchivos() {
        List<Archivo> archivos = Archivo.listAll();
        if (archivos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(archivos).build();
    }

    @PATCH
    @Path("/{archivoId}/marcar-enviado")
    public Response marcarArchivoComoEnviado(@PathParam("archivoId") Long archivoId) {
        Archivo archivo = Archivo.findById(archivoId);
        if (archivo == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Archivo no encontrado").build();
        }

        if (archivo.estado != EstadoArchivo.GENERADO) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("El archivo debe estar en estado GENERADO para poder marcarse como ENVIADO").build();
        }

        archivo.estado = EstadoArchivo.ENVIADO;
        archivo.persist();  // Guardar los cambios

        return Response.ok("Archivo marcado como ENVIADO").build();
    }

    @GET
    @Path("/{archivoId}/descargar")
    @Produces(MediaType.TEXT_PLAIN)
    public Response descargarArchivo(@PathParam("archivoId") Long archivoId) {
        Archivo archivo = Archivo.findById(archivoId);
        if (archivo == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Archivo no encontrado").build();
        }

        // Generar el nombre del archivo con el formato YYYYMMDD-NombreDeEntidad.txt
        String nombreArchivo = archivo.fechaGeneracion.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-" + archivo.entidadCrediticia.nombre + ".txt";

        return Response.ok(archivo.archivo)
                .header("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"")
                .build();
    }

    @Inject
    ArchivoService archivoService;

    @POST
    @Path("/{archivoId}/procesar")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response procesarArchivo(@PathParam("archivoId") Long archivoId, InputStream streamArchivoEntidad) {
        // Buscar el archivo
        Archivo archivo = archivoRepository.findById(archivoId);
        if (archivo == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Archivo no encontrado").build();

        } else if (!archivo.estado.equals(EstadoArchivo.ENVIADO)) {
            return Response.status(Response.Status.CONFLICT).entity("El archivo ya se encuentra procesado").build();
        }

        // Leer el contenido del archivo
        String contenido;
        try {
            contenido = new String(streamArchivoEntidad.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al leer el archivo proporcionado por la entidad").build();
        }

        // Procesar el archivo
        boolean hayErrores = false;
        StringBuilder detalleErrores = new StringBuilder();

        // Procesar el contenido del archivo
        switch (archivo.entidadCrediticia.nombre){
            case "visa" -> {
                archivoService.procesarArchivoVisa(archivo, contenido, detalleErrores);
            }
            case "master" -> {
                archivoService.procesarArchivoMaster(archivo, contenido, detalleErrores);
            }
            default -> {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(String.format("No existe implementación para la entidad %s",archivo.entidadCrediticia.nombre)).build();
            }
        }


        //
        if (!detalleErrores.isEmpty()) {
            archivo.estado = EstadoArchivo.ERROR;
            archivo.detalleErrores = detalleErrores.toString();
        } else {
            archivo.estado = EstadoArchivo.PROCESADO;
        }
        archivoRepository.persist(archivo);

        return Response.ok(archivo).build();
    }


}
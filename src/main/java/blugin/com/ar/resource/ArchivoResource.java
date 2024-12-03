package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.ArchivoRepository;
import blugin.com.ar.service.ArchivoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@ResourceProperties(paged = false)
@Path("/archivo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class ArchivoResource {
    private static final Logger log = LoggerFactory.getLogger(ArchivoResource.class); //extends PanacheRepositoryResource<ArchivoProcesamientoRepository, ArchivoProcesamiento, Long> { }

    @Inject
    ArchivoRepository archivoRepository;

    //@Inject
    //PagoService pagoService;

    @GET
    @Path("/{archivoId}")
    public Response getArchivo(@PathParam("archivoId") Long archivoId) {

        //
        Archivo  archivo = Archivo.findById(archivoId);

        if(archivo != null) {
            return Response.status(Response.Status.OK).entity(archivo).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @DELETE
    @Path("/{archivoId}")
    public Response elimianrArchivo(@PathParam("archivoId") Long archivoId) {

        //
        Archivo  archivo = Archivo.findById(archivoId);

        if(archivo != null) {
            //if( (archivo.facturas!=null) && (!archivo.facturas.isEmpty() )){
                archivo.delete();
                return Response.status(Response.Status.ACCEPTED).build();
            //}else {
            //    return Response.status(Response.Status.CONFLICT).entity("No es posible eliminar, el archivo contiene facturas asociadas").build();
            //}
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Archivo no encontrado").build();
        }
    }

    @GET
    public Response getArchivos(@QueryParam("entidadId") Long entidadId,
                                @QueryParam("desde") LocalDate desde,
                                @QueryParam("hasta") LocalDate hasta) {
        if(desde == null){
            desde = LocalDate.now();
        }

        if(hasta == null){
            hasta = LocalDate.now();
        }

        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.atTime(LocalTime.MAX);

        // Consultar pagos recibidos en el periodo
        List<Archivo> archivos;

        if(entidadId!=null){

            if (EntidadCrediticia.findById(entidadId) == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Entidad crediticia no encontrada").build();
            }
            archivos = Archivo.find("entidadCrediticia.id = ?1 and (fechaGeneracion >= ?2 and fechaGeneracion <= ?3)", entidadId, inicio, fin).list();
        } else{
            archivos = Archivo.find("fechaGeneracion >= ?1 and fechaGeneracion <= ?2", inicio, fin).list();
        }

        //List<Archivo> archivos = archivoRepository.findAll().list();

        if(!archivos.isEmpty()) {
            return Response.status(Response.Status.OK).entity(archivos).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
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
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("No se encontraron facturas emitidas para la entidad crediticia en la lista proporcionada.")
                    .build();
        }

        // Crear el archivo en la entidad Archivo
        Archivo archivo = new Archivo();

        //
        BigDecimal importeTotal = BigDecimal.ZERO;
        for (Factura factura : facturas) {
            factura.setArchivo(archivo);
            importeTotal = importeTotal.add(factura.total);
        }

        archivo.facturas = facturas;
        archivo.entidadCrediticia = entidadCrediticia;
        archivo.importeTotal = importeTotal;

        archivo.archivo = archivoService.generarContenidoArchivo(archivo);

        archivo.fechaGeneracion = LocalDateTime.now();
        archivo.estado = EstadoArchivo.GENERADO;
        archivo.persist();

        return Response.ok(archivo).build();
    }


    @PATCH
    @Path("/{archivoId}/marcar-enviado")
    public Response marcarArchivoComoEnviado(@PathParam("archivoId") Long archivoId) {
        Archivo archivo = Archivo.findById(archivoId);
        if (archivo == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Archivo no encontrado").build();
        }

        if (archivo.estado == EstadoArchivo.GENERADO ) {
            archivo.estado = EstadoArchivo.ENVIADO;
            archivo.persist();  // Guardar los cambios

            return Response.ok("Archivo marcado como ENVIADO").build();
        }else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("El archivo debe estar en estado GENERADO para poder marcarse como ENVIADO").build();
        }
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
        } else if (archivo.estado.equals(EstadoArchivo.PROCESADO) || archivo.estado.equals(EstadoArchivo.ERROR)) {
            return Response.status(Response.Status.CONFLICT).entity("El archivo ya se encuentra procesado").build();
        } else if (archivo.estado.equals(EstadoArchivo.GENERADO)) {
            return Response.status(Response.Status.CONFLICT).entity("El estado del archivo registra que no se ha enviado").build();
        }

            // Leer el contenido del archivo
        String contenido;
        try {
            contenido = new String(streamArchivoEntidad.readAllBytes(), StandardCharsets.UTF_8);

            if(contenido.isEmpty()){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al leer el archivo proporcionado por la entidad, sin contenido!").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al leer el archivo proporcionado por la entidad").build();
        }

        // Procesar el archivo
        boolean hayErrores = false;
        StringBuilder detalleErrores = new StringBuilder();

        // Procesar el contenido del archivo
        if (archivo.entidadCrediticia.id == 1){
            //"visa"
            archivoService.procesarArchivoVisa(archivo, contenido, detalleErrores);

        } else if (archivo.entidadCrediticia.id == 2) {
            //"master"
            archivoService.procesarArchivoMaster(archivo, contenido, detalleErrores);
        } else {
            //Otro no implementado
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(String.format("No existe implementación para la entidad %s",archivo.entidadCrediticia.nombre)).build();
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
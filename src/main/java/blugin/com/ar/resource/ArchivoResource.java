package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.dto.ArchivoDTO;
import blugin.com.ar.dto.LoteFacturaDTO;
import blugin.com.ar.repository.ArchivoRepository;
import blugin.com.ar.service.ArchivoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Archivo archivo = Archivo.findById(archivoId);

        if (archivo != null) {
            return Response.status(Response.Status.OK).entity(archivo).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @DELETE
    @Path("/{archivoId}")
    public Response elimianrArchivo(@PathParam("archivoId") Long archivoId) {

        //
        Archivo archivo = Archivo.findById(archivoId);

        if (archivo != null) {
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
        if (desde == null) {
            desde = LocalDate.now();
        }

        if (hasta == null) {
            hasta = LocalDate.now();
        }

        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.atTime(LocalTime.MAX);

        // Consultar pagos recibidos en el periodo
        List<Archivo> archivos;

        if (entidadId != null) {

            if (EntidadCrediticia.findById(entidadId) == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Entidad crediticia no encontrada").build();
            }
            archivos = Archivo.find("entidadCrediticia.id = ?1 and (fechaGeneracion >= ?2 and fechaGeneracion <= ?3)", entidadId, inicio, fin).list();
        } else {
            archivos = Archivo.find("fechaGeneracion >= ?1 and fechaGeneracion <= ?2", inicio, fin).list();
        }

        //List<Archivo> archivos = archivoRepository.findAll().list();

        if (!archivos.isEmpty()) {
            List<ArchivoDTO> archivosDTO = archivos.stream()
                    .map(ArchivoDTO::new)
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(archivosDTO).build();
            //return Response.status(Response.Status.OK).entity(archivos).build();
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
        List<Factura> facturas =
                (facturasIds == null || facturasIds.isEmpty())
                        // todas las posibles
                        ? Factura.list("socio.entidadCrediticia.id = ?1 and estado = ?2",
                        entidadId, EstadoFactura.EMITIDA)
                        // solo las que me pasan
                        : Factura.list("id in ?1 and socio.entidadCrediticia.id = ?2 and estado = ?3",
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
        BigDecimal pagosParciales = BigDecimal.ZERO;

        for (Factura factura : facturas) {
            factura.setArchivo(archivo);
            importeTotal = importeTotal.add(factura.getSaldo());
        }

        archivo.facturas = facturas;
        archivo.entidadCrediticia = entidadCrediticia;
        archivo.importeTotal = importeTotal;
        archivo.fechaGeneracion = LocalDateTime.now();

        archivo.archivo = archivoService.generarContenidoArchivo(archivo);

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

        if (archivo.estado == EstadoArchivo.GENERADO) {
            archivo.estado = EstadoArchivo.ENVIADO;
            archivo.persist();  // Guardar los cambios

            return Response.ok("Archivo marcado como ENVIADO").build();
        } else {
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
    @Produces(MediaType.TEXT_PLAIN)
    public Response procesarArchivo(@PathParam("archivoId") Long archivoId, InputStream streamArchivoEntidad) {

        Archivo archivo = Archivo.findById(archivoId);

        if (archivo == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("El archivo no se encuentra").build();
        }


        List<String> lineasArchivo = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(streamArchivoEntidad))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                lineasArchivo.add(linea);
            }

            if (lineasArchivo.size() >= 3 ) {

                String salida = archivoService.procesarArchivo(archivo, lineasArchivo);

                //guardamos los datos en el archivo
                archivo.persist();

                //enviamos la salida
                return Response.status(Response.Status.OK)
                        .entity(salida).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El archivo no parece ser válido").build();
            }

        } catch (IOException ex) {
            return Response.serverError().entity(ex.getMessage()).build();
        }

    }
}
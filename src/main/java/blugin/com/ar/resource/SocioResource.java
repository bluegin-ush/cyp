package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.dto.*;
import blugin.com.ar.repository.*;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/socio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SocioResource {// implements PanacheRepositoryResource<SocioRepository, Socio, Long> {

    @Inject
    SocioRepository socioRepository;

    @Inject
    FacturaRepository facturaRepository;

    @Inject
    ServicioRepository servicioRepository;

    @Inject
    EntidadCrediticiaRepository entidadRepository;

    @GET
    public List<Socio> getAllSocios() {

        //
        List<Socio> socios = socioRepository.listAll();

        //
        for (Socio s : socios){
            s.tieneDeuda = facturaRepository.findImpagasBySocioId(s.id).size()>0;
        }

        return socios;
    }

    @GET
    @Path("/proximo-numero")
    public Long getProximoNumero() {

        return socioRepository.obtenerSiguienteNroDeSocio();
    }

    @GET
    @Path("{socioId}")
    public Socio getSocio(@PathParam("socioId") Long socioId) {
        return socioRepository.findById(socioId);
    }

    @POST
    @Transactional
    public Response createSocio(Socio socio) {

        if (socio.nombre == null || socio.nombre.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("El nombre del socio es obligatorio").build();
        } else if (socio.tipoDoc == null || socio.tipoDoc.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("El tipo de documento del socio es obligatorio").build();
        } else if (socio.numDoc == null || socio.numDoc == 0L) {
            return Response.status(Response.Status.BAD_REQUEST).entity("El número de documento del socio es obligatorio").build();
        }

        if (socio.nro == null) {
            socio.nro = socioRepository.obtenerSiguienteNroDeSocio();
        }

        // Registramos el alta del socio

        Registro registro = new Registro();
        registro.fecha = LocalDate.now();
        registro.tipo = Registro.Tipo.ALTA;
        registro.motivo = "Alta del socio";
        registro.setSocio(socio);

        registro.persist();

        // Persistencia del socio
        socio.agregarRegistro(registro);
        socio.persist();

        // Construcción de la respuesta
        return Response.ok(socio).status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{socioId}/servicios")
    public Response getServiciosBySocio(@PathParam("socioId") Long socioId) {
        Socio socio = socioRepository.findById(socioId);
        if (socio != null) {
            return Response.ok(socio.servicios).build();
        } else {
            //
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }
    }

    @POST
    @Path("/{socioId}/servicio/{servicioId}")
    @Transactional
    public Response addServicioASocioPorId(Long socioId, Long servicioId) {
        Socio socio = socioRepository.findById(socioId);
        Servicio servicio = servicioRepository.findById(servicioId);

        // Validaciones
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }
        if (servicio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Servicio no encontrado").build();
        }
        if (socio.servicios.contains(servicio)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("El servicio ya está asociado al socio").build();
        }

        //
        socio.servicios.add(servicio);
        socio.persist();

        //
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{socioId}/servicio/{servicioId}")
    @Transactional
    public Response removeServicioDeSocio(@PathParam("socioId") Long socioId, @PathParam("servicioId") Long servicioId) {
        //socioServicioRepository.delete("socio.id = ?1 and servicio.id = ?2", socioId, servicioId);
        Socio socio = socioRepository.findById(socioId);
        Servicio servicio = servicioRepository.findById(servicioId);

        // Validaciones
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }
        if (servicio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Servicio no encontrado").build();
        }
        if (!socio.servicios.contains(servicio)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("El servicio no está asociado al socio").build();
        }

        //
        socio.servicios.remove(servicio);
        socio.persist();

        //
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{socioId}/baja")
    @Transactional
    public Response bajaSocio(@PathParam("socioId") Long socioId, String motivo) {

        Socio socio = socioRepository.findById(socioId);

        // Validaciones
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        } else if (!socio.activo) {
            return Response.status(Response.Status.CONFLICT).entity("El socio ya esta dado de baja").build();
        } else if (socio.ctacte.compareTo(BigDecimal.ZERO) < 0) {
            return Response.status(Response.Status.CONFLICT).entity("El socio tiene deuda").build();
        }


        // Registramos el alta del socio

        Registro registro = new Registro();
        registro.fecha = LocalDate.now();
        registro.tipo = Registro.Tipo.BAJA;
        registro.motivo = (motivo != null && !motivo.isEmpty() ? motivo : "baja del socio");
        registro.setSocio(socio);

        registro.persist();

        // Persistencia del socio
        socio.activo = false;
        socio.agregarRegistro(registro);
        socio.persist();


        // Construcción de la respuesta
        return Response.ok(socio).status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Path("{socioId}/alta")
    @Transactional
    public Response altaSocio(@PathParam("socioId") Long socioId, String motivo) {

        Socio socio = socioRepository.findById(socioId);

        // Validaciones
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        } else if (socio.activo) {
            return Response.status(Response.Status.CONFLICT).entity("El socio ya esta habilitado").build();
        }


        // Registramos el alta del socio

        Registro registro = new Registro();
        registro.fecha = LocalDate.now();
        registro.tipo = Registro.Tipo.ALTA;
        registro.motivo = (motivo != null && !motivo.isEmpty() ? motivo : "rehabilitación del socio");
        registro.setSocio(socio);

        registro.persist();

        // Persistencia del socio
        socio.activo = true;
        socio.agregarRegistro(registro);
        socio.persist();


        // Construcción de la respuesta
        return Response.ok(socio).status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Path("{socioId}")
    @Transactional
    public Response modificarSocio(@PathParam("socioId") Long socioId, Socio socioNew) {

        Socio socio = socioRepository.findById(socioId);

        // Validaciones
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }

        if (socioNew.numDoc != null) {
            socio.numDoc = socioNew.numDoc;
        }

        if (socioNew.tipoDoc != null) {
            socio.tipoDoc = socioNew.tipoDoc;
        }

        if (socioNew.apellido != null) {
            socio.apellido = socioNew.apellido;
        }

        if (socioNew.nombre != null) {
            socio.nombre = socioNew.nombre;
        }

        if (socioNew.telefono != null) {
            socio.telefono = socioNew.telefono;
        }

        if (socioNew.domicilio != null) {
            socio.domicilio = socioNew.domicilio;
        }

        if (socioNew.correo != null && !socioNew.correo.isEmpty()) {
            socio.correo = socioNew.correo;
        }

        if (socioNew.tarjetaNum != null) {
            socio.tarjetaNum = socioNew.tarjetaNum;
        }

        if (socioNew.tarjetaVto != null) {
            socio.tarjetaVto = socioNew.tarjetaVto;
        }

        socio.persist();

        // Construcción de la respuesta
        return Response.ok(socio).status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("/{socioId}/entidad/{entidadId}")
    @Transactional
    public Response addEntidadASocioPorId(Long socioId, Long entidadId) {
        Socio socio = socioRepository.findById(socioId);
        EntidadCrediticia entidad = entidadRepository.findById(entidadId);

        // Validaciones
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }
        if (entidad == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entidad no encontrada").build();
        }
        if (socio.entidadCrediticia != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Ya se encuentra vinculado con una entidad").build();
        }

        //
        socio.entidadCrediticia = entidad;
        socio.persist();

        //
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{socioId}/entidad")
    @Transactional
    public Response removeEntidadASocioPorId(Long socioId, Long entidadId) {
        Socio socio = socioRepository.findById(socioId);

        // Validaciones
        if (socio == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }

        if (socio.entidadCrediticia == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No se encuentra vinculado con alguna entidad").build();
        }

        //
        socio.entidadCrediticia = null;
        socio.persist();

        //
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/entidad/{entidadId}")
    public Response getSociosByEntidad(@PathParam("entidadId") Long entidadId) {

        List<Socio> socios = socioRepository.find("entidadCrediticia.id = ?1", entidadId).list();

        //
        if (socios == null || socios.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {

            return Response.status(Response.Status.OK).entity(socios).build();

        }
    }

    @GET
    @Path("/sin-entidad")
    public Response getSociosSinEntidad(@QueryParam("servicio") Boolean servicio) {

        List<Socio> socios;

        if (servicio == null) {
            socios = socioRepository.todosSinEntidad();
        } else {
            socios = socioRepository.todosSinEntidadYServicio(servicio);
        }

        //
        if (socios == null || socios.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {

            return Response.status(Response.Status.OK).entity(socios).build();

        }
    }

    @GET
    @Path("/con-entidad/{mes}/{anio}")
    public Response getSociosConEntidad(@PathParam("mes") int mes,
                                        @PathParam("anio") int anio) {

        // Convertir mes y año en rango de fechas
        LocalDateTime inicioMes = LocalDateTime.of(anio, mes, 1, 0, 0);
        LocalDateTime finMes = inicioMes.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        List<Socio> socios;

        //Obtengo  los socios de la entidad
        socios = socioRepository.todosConEntidadYServicio();

        //
        if (socios == null || socios.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {

            // Obtengo las facturas de cada socio según el periodo inicio - fin

            List<SocioDTO> sociosDTO = new ArrayList<>();
            for (Socio socio : socios) {

                SocioDTO socioDTO = SocioMapper.toDTO(socio);

                socioDTO.entidadCrediticia = EntidadCrediticiaMapper.toDTO(socio.entidadCrediticia);

                //Obtengo las faturas del periodo de cada socio
                List<Factura> facturas = facturaRepository.buscarFacturasEntreFechasPorSocio(socio.id, inicioMes, finMes);

                for (Factura factura : facturas) {
                    FacturaDTO facturaDTO = new FacturaDTO(factura);
                    socioDTO.addFactura(facturaDTO);
                }

                sociosDTO.add(socioDTO);

            }

            return Response.status(Response.Status.OK).entity(sociosDTO).build();

        }
    }

    /*@GET
    @Path("/entidad-crediticia/{entidadId}")
    public Response getSociosByEntidad(@PathParam("entidadId") Long entidadId) {
        List<Socio> socios = socioRepository.findByEntidadId(entidadId);
        if (socios != null) {
            return Response.ok(socios).build();
        } else {
            //
            return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
        }
    }*/
}
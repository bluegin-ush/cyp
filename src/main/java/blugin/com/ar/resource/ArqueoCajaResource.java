package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.dto.ArqueoCajaDTO;
import blugin.com.ar.repository.ArqueoCajaRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/arqueo-caja")
public class ArqueoCajaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response realizarArqueo(@QueryParam("desde") LocalDate desde,
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
        List<Pago> pagos = Pago.find("fecha >= ?1 and fecha <= ?2", inicio, fin).list();

        // Consultar salidas de efectivo en el periodo
        List<Salida> salidas = Salida.find("fecha >= ?1 and fecha <= ?2", inicio, fin).list();

        // Agrupar ingresos por tipo de medio de pago
        Map<MedioDePago, BigDecimal> ingresosPorTipo = pagos.stream()
                .collect(Collectors.groupingBy(
                        pago -> pago.medioDePago,  // Agrupar por medio de pago
                        Collectors.reducing(BigDecimal.ZERO, pago -> pago.monto, BigDecimal::add)
                ));

        // Agrupar salidas por tipo de salida
        Map<TipoSalida, BigDecimal> salidasPorTipo = salidas.stream()
                .collect(Collectors.groupingBy(
                        salida -> salida.tipo,  // Agrupar por tipo de salida
                        Collectors.reducing(BigDecimal.ZERO, salida -> salida.monto, BigDecimal::add)
                ));

        // Calcular total de ingresos y salidas
        BigDecimal totalEntradas = ingresosPorTipo.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSalidas = salidasPorTipo.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoFinal = totalEntradas.subtract(totalSalidas);

        // Crear un objeto de respuesta con los detalles
        ArqueoCajaDTO arqueoDTO = new ArqueoCajaDTO();
        arqueoDTO.totalEntradas = totalEntradas;
        arqueoDTO.totalSalidas = totalSalidas;
        arqueoDTO.saldoFinal = saldoFinal;
        arqueoDTO.ingresosPorTipo = ingresosPorTipo;
        arqueoDTO.salidasPorTipo = salidasPorTipo;

        arqueoDTO.pagos = pagos;
        arqueoDTO.salidas = salidas;

        return Response.ok(arqueoDTO).build();
    }

    /*@GET
    @Path("/detalle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response realizarArqueoDetalle(@QueryParam("desde") LocalDate desde,
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
        List<Pago> pagos = Pago.find("fecha >= ?1 and fecha <= ?2", inicio, fin).list();

        // Consultar salidas de efectivo en el periodo
        List<Salida> salidas = Salida.find("fecha >= ?1 and fecha <= ?2", inicio, fin).list();

        return Response.ok(QUE?).build();
    }*/
}
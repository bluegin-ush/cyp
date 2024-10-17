package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*; // Asegúrate de importar tus modelos correctamente
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConfiguracionRepository implements PanacheRepository<Configuracion> {
    public Map<String, String> obtenerTodasLasConfiguraciones() {
        //
        return findAll().stream()
                .collect(Collectors.toMap(
                        config -> config.clave,  // Clave del Map
                        config -> config.valor   // Valor del Map
                ));
    }
}

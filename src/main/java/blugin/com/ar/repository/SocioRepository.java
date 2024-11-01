package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SocioRepository implements PanacheRepository<Socio> {

    public List<Socio> findByEntidadId(Long entidadId) {
        return find("entidadCrediticia.id", entidadId).list();
    }

    public Long obtenerSiguienteNroDeSocio() {
        Socio socio = find("ORDER BY nro DESC").firstResult();
        return (socio != null) ? socio.nro + 1 : 1;
    }

    public List<Socio> todosSinEntidad() {
        return find("entidadCrediticia is null").list();
    }

    public List<Socio> todosSinEntidadYServicio(Boolean conServicio) {
        if(conServicio) {
            return find("(entidadCrediticia is null) and (servicios is not empty)").list();
        }else{
            return find("(entidadCrediticia is null) and (servicios is empty)").list();
        }
    }

}

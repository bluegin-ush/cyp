package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.*; // Asegúrate de importar tus modelos correctamente
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@Deprecated
//@ApplicationScoped
public class ArqueoCajaRepository implements PanacheRepository<ArqueoCaja> { }

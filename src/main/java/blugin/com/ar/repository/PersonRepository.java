package blugin.com.ar.repository;

import blugin.com.ar.cyp.model.Person;
import blugin.com.ar.cyp.model.Status;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

    public List<Person> findByName(String name){
        return find("name", name).firstResult();
    }

    public List<Person> findAlive(){
        return list("status", Status.Alive);
    }

    public void deleteDaniel(){
        delete("name", "Daniel");
    }
}

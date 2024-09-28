package blugin.com.ar.service;

import blugin.com.ar.cyp.model.Person;
import blugin.com.ar.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public void createPerson(Person person) {
        personRepository.persist(person);
    }

    public List<Person> findByName(String name) {
        return personRepository.findByName(name);
    }
}
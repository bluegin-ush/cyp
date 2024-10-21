package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.Person;
import blugin.com.ar.repository.PersonRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//@Path("/persons")
@ResourceProperties(paged = false)
public interface PersonResource{ // extends PanacheRepositoryResource<PersonRepository,Person, Long> {
    @GET
    @Path("{id}/primos")
    default public List<Person> getPrimos(@PathParam("id") Long id){
            Person primo = new Person();
            return List.of(primo);
    }
/*
    @GET
    List<Person> getAllPersons();

    @GET
    @Path("/{id}")
    Person getPersonById(@PathParam("id") Long id);

    @POST
    @Transactional
    Response createPerson(Person person);

    @PUT
    @Path("/{id}")
    @Transactional
    Response updatePerson(@PathParam("id") Long id, Person person);

 */
}

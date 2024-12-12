package blugin.com.ar.resource;

import blugin.com.ar.cyp.model.*;
import blugin.com.ar.repository.UsuarioRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import java.util.List;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioRepository usuarioRepository;

    //
    @GET
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.listAll();
    }

    //
    @GET
    @Path("/{id}")
    public Usuario obtenerUsuarioPorId(@PathParam("id") Long id) {
        return usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Usuario no encontrado", Response.Status.NOT_FOUND));
    }

    // POST: Crear un nuevo usuario
    @POST
    @Transactional
    public Response crearUsuario(Usuario usuario) {
        usuarioRepository.persist(usuario);
        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    // PUT: Actualizar un usuario existente
    @PUT
    @Path("/{id}")
    @Transactional
    public Usuario actualizarUsuario(@PathParam("id") Long id, Usuario usuario) {
        Usuario existente = usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Usuario no encontrado", Response.Status.NOT_FOUND));
        // Actualiza los campos necesarios
        if (usuario.nombre!=null)
            existente.nombre = usuario.nombre;
        if (usuario.usuario!=null)
            existente.usuario = usuario.usuario;
        if (usuario.clave!=null)
            existente.clave = usuario.clave;
        if (usuario.rol!=null)
            existente.rol = usuario.clave;
        usuarioRepository.persist(existente);
        return existente;
    }

    // DELETE: Eliminar un usuario por ID
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminarUsuario(@PathParam("id") Long id) {
        boolean eliminado = usuarioRepository.deleteById(id);
        if (!eliminado) {
            throw new WebApplicationException("Usuario no encontrado", Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }
}


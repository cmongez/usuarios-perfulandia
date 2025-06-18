package cl.perfulandia.usuarios.assemblers;

import cl.perfulandia.usuarios.controller.UsuarioController;
import cl.perfulandia.usuarios.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Ensamblador HATEOAS para la entidad Usuario.
 * Su función es envolver un objeto Usuario dentro de un EntityModel que contiene enlaces navegables (hypermedia).
 * Esto permite que la API sea más autoexplicativa y orientada a acciones posibles sobre los recursos.
 */
@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    /**
     * Transforma un objeto Usuario en un EntityModel que incluye enlaces HATEOAS.
     *
     * @param usuario el objeto Usuario que se va a transformar
     * @return un EntityModel con los datos del usuario y enlaces útiles
     */
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
            // Enlace al recurso actual (GET /usuarios/{id})
            linkTo(methodOn(UsuarioController.class).obtenerPorId(usuario.getId())).withSelfRel(),

            // Enlace a la lista completa de usuarios (GET /usuarios)
            linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios"),

            // Enlace para eliminar este usuario (DELETE /usuarios/{id})
            linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getId())).withRel("eliminar"),

            // Enlace para actualizar este usuario (PUT /usuarios/{id})
            linkTo(methodOn(UsuarioController.class).actualizarUsuario(usuario.getId(), usuario)).withRel("actualizar")
        );
    }
}
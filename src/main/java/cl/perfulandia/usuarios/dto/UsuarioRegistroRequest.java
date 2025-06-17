package cl.perfulandia.usuarios.dto;

import cl.perfulandia.usuarios.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para registrar un nuevo usuario con su rol asociado")
public class UsuarioRegistroRequest {

    @Schema(description = "Objeto con los datos del nuevo usuario",
            implementation = Usuario.class,
            required = true)
    private Usuario usuario;

    @Schema(description = "Nombre del rol que se asignará al usuario",
            example = "CLIENTE",
            required = true)
    private String nombreRol;
}

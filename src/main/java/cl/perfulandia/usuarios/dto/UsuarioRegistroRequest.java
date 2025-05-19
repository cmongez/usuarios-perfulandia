package cl.perfulandia.usuarios.dto;

import cl.perfulandia.usuarios.model.Usuario;
import lombok.Data;

@Data
public class UsuarioRegistroRequest {
    private Usuario usuario;
    private String nombreRol;
}

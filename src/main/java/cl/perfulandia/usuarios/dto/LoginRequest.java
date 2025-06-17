package cl.perfulandia.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Credenciales de acceso para login")
public class LoginRequest {
    @Schema(description = "Nombre de usuario", example = "juan123", required = true)
    private String username;
    @Schema(description = "Contraseña del usuario", example = "1234", required = true)
    private String password;
}

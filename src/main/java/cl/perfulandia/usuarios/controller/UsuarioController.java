package cl.perfulandia.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.perfulandia.usuarios.dto.LoginRequest;
import cl.perfulandia.usuarios.dto.UsuarioRegistroRequest;
import cl.perfulandia.usuarios.model.Usuario;
import cl.perfulandia.usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Tag(name = "Usuarios", description = "Operaciones CRUD para usuarios y autenticación")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtiene todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class)))
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @Operation(summary = "Registra un nuevo usuario con su rol")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/auth/registro")
    public ResponseEntity<Usuario> registrar(
        @RequestBody(description = "Datos del usuario y nombre del rol", required = true)
        UsuarioRegistroRequest request) {
        Usuario usuario = usuarioService.registrarUsuario(request.getUsuario(), request.getNombreRol());
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Inicia sesión con username y password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<Usuario> login(
        @RequestBody(description = "Credenciales de login", required = true)
        LoginRequest request) {
        Usuario usuario = usuarioService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Obtiene un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@Parameter(description = "ID del usuario")@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Obtiene usuarios por nombre de rol")
    @ApiResponse(responseCode = "200", description = "Usuarios encontrados",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class)))
    @GetMapping("/rol/{nombreRol}")
    public ResponseEntity<List<Usuario>> obtenerPorRol(@PathVariable String nombreRol) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol(nombreRol);
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Actualiza los datos de un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
        @PathVariable Long id,
        @RequestBody(description = "Nuevos datos del usuario", required = true)
        Usuario datosNuevos) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, datosNuevos);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @Operation(summary = "Elimina un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}

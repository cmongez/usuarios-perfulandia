package cl.perfulandia.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.perfulandia.usuarios.dto.LoginRequest;
import cl.perfulandia.usuarios.dto.UsuarioRegistroRequest;
import cl.perfulandia.usuarios.model.Usuario;
import cl.perfulandia.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Registrar nuevo usuario
    @PostMapping("/auth/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody UsuarioRegistroRequest request) {
        Usuario usuario = usuarioService.registrarUsuario(request.getUsuario(), request.getNombreRol());
        return ResponseEntity.ok(usuario);
    }

    // Login de usuario
    @PostMapping("/auth/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(usuario);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // Obtener usuarios por nombre de rol
    @GetMapping("/rol/{nombreRol}")
    public ResponseEntity<List<Usuario>> obtenerPorRol(@PathVariable String nombreRol) {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol(nombreRol);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario datosNuevos) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, datosNuevos);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}

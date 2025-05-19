package cl.perfulandia.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.perfulandia.usuarios.dto.LoginRequest;
import cl.perfulandia.usuarios.dto.UsuarioRegistroRequest;
import cl.perfulandia.usuarios.model.Usuario;
import cl.perfulandia.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/auth/registro")
    public ResponseEntity<?> registrar(@RequestBody UsuarioRegistroRequest request) {
        System.out.println(request);
        Usuario usuario = usuarioService.registrarUsuario(request.getUsuario(), request.getNombreRol());
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Rol no encontrado");
        }

        return ResponseEntity.ok(usuario);
    }

    // Login de usuario
    @PostMapping("/auth/login")
    public Usuario login(@RequestBody LoginRequest request) {
        return usuarioService.login(request.getUsername(), request.getPassword());
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // Obtener usuarios por nombre de rol
    @GetMapping("/rol/{nombreRol}")
    public List<Usuario> obtenerPorRol(@PathVariable String nombreRol) {
        return usuarioService.obtenerUsuariosPorRol(nombreRol);
    }
}

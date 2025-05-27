package cl.perfulandia.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.model.Usuario;
import cl.perfulandia.usuarios.repository.RolRepository;
import cl.perfulandia.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Registrar nuevo usuario
    public Usuario registrarUsuario(Usuario usuario, String nombreRol) {
        Optional<Rol> rolEncontrado = rolRepository.findByNombre(nombreRol);

        if (rolEncontrado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no encontrado: " + nombreRol);
        }
        usuario.setRol(rolEncontrado.get());
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    // Iniciar sesión
    public Usuario login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!usuario.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña incorrecta");
        }

        if (!usuario.isActivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El usuario está inactivo");
        }

        return usuario;
    }

    // Buscar por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + id));
    }

    // Buscar por nombre de rol
    public List<Usuario> obtenerUsuariosPorRol(String nombreRol) {
        return usuarioRepository.findByRol_Nombre(nombreRol);
    }

    public Usuario actualizarUsuario(Long id, Usuario datosNuevos) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);
        usuarioExistente.setUsername(datosNuevos.getUsername());
        usuarioExistente.setEmail(datosNuevos.getEmail());
        usuarioExistente.setNombre(datosNuevos.getNombre());
        usuarioExistente.setApellido(datosNuevos.getApellido());
        usuarioExistente.setRut(datosNuevos.getRut());
        usuarioExistente.setDireccion(datosNuevos.getDireccion());
        return usuarioRepository.save(usuarioExistente);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

}

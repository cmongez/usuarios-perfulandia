package cl.perfulandia.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.model.Usuario;
import cl.perfulandia.usuarios.repository.RolRepository;
import cl.perfulandia.usuarios.repository.UsuarioRepository;

public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    // Registrar nuevo usuario
    public Usuario registrarUsuario(Usuario usuario, String nombreRol) {
        Optional<Rol> rolEncontrado = rolRepository.findByNombre(nombreRol);

        if (rolEncontrado.isEmpty()) {
            throw new RuntimeException("Rol no encontrado: " + nombreRol);
        }
        usuario.setRol(rolEncontrado.get());
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    // Iniciar sesión
    public Usuario login(String username, String password) {
        Optional<Usuario> resultado = usuarioRepository.findByUsername(username);

        if (resultado.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = resultado.get();

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.isActivo()) {
            throw new RuntimeException("El usuario está inactivo");
        }

        return usuario;
    }

    // Buscar por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> resultado = usuarioRepository.findById(id);

        if (resultado.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        return resultado.get();
    }


    // Buscar por nombre de rol
    public List<Usuario> obtenerUsuariosPorRol(String nombreRol) {
        return usuarioRepository.findByRol_Nombre(nombreRol);
    }
}

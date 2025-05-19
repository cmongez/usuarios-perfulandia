package cl.perfulandia.usuarios.service;

import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Crear un nuevo rol
    public Rol registrarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // Listar todos los roles
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findAll();
    }

    // Buscar un rol por ID
    public Rol obtenerRolPorId(Long id) {
        Optional<Rol> resultado = rolRepository.findById(id);

        if (resultado.isEmpty()) {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }

        return resultado.get();
    }

    // (Opcional) Buscar por nombre
    public Rol obtenerRolPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con nombre: " + nombre));
    }
}

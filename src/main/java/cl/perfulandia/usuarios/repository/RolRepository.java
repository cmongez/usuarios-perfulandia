package cl.perfulandia.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.perfulandia.usuarios.model.Rol;

public interface RolRepository extends JpaRepository <Rol,Long>{
    Optional<Rol> findByNombre(String nombre);
}

package cl.perfulandia.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    //Crear un nuevo rol
    @PostMapping
    public Rol crearRol(@RequestBody Rol rol){
        return rolService.registrarRol(rol);
    }

    //Obtener todos los roles
    @GetMapping
    public List<Rol> obtenerRoles(){
        return rolService.obtenerTodosLosRoles();
    }

    //Obtener un rol por ID
    @GetMapping("/{id}")
    public Rol obtenerPorId(@PathVariable Long id){
        return rolService.obtenerRolPorId(id);
    }
}

package cl.perfulandia.usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Crea un nuevo rol")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Rol crearRol(
        @RequestBody(
            description = "Datos del nuevo rol",
            required = true,
            content = @Content(schema = @Schema(implementation = Rol.class))
        )
        @org.springframework.web.bind.annotation.RequestBody Rol rol) {
        return rolService.registrarRol(rol);
    }

    @Operation(summary = "Obtiene todos los roles registrados")
    @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Rol.class)))
    @GetMapping
    public List<Rol> obtenerRoles() {
        return rolService.obtenerTodosLosRoles();
    }


    @Operation(summary = "Obtiene un rol por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{id}")
    public Rol obtenerPorId(@PathVariable Long id) {
        return rolService.obtenerRolPorId(id);
    }
}

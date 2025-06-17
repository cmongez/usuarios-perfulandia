package cl.perfulandia.usuarios.service;

import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.repository.RolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RolServiceTest {
    // Inyecta el servicio RolService para probar su comportamiento real.
    @Autowired
    private RolService rolService;

    // Crea un mock del repositorio RolRepository para simular su comportamiento sin acceder a la base de datos.
    @MockBean
    private RolRepository rolRepository;
    
    @Test
    public void testRegistrarRol() {
        Rol rol = new Rol(1L, "ADMIN");

        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto rol proporcionado.
        when(rolRepository.save(rol)).thenReturn(rol);

        // Llama al método registrarRol() del servicio.
        Rol resultado = rolService.registrarRol(rol);

        // Verifica que el rol guardado no sea nulo y que tenga el nombre correcto.
        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombre());
    }

    @Test
    public void testObtenerTodosLosRoles() {
        Rol rol = new Rol(2L, "CLIENTE");

        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un solo rol.
        when(rolRepository.findAll()).thenReturn(List.of(rol));

        // Llama al método obtenerTodosLosRoles() del servicio.
        List<Rol> resultado = rolService.obtenerTodosLosRoles();

        // Verifica que la lista no sea nula y contenga exactamente un elemento.
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testObtenerRolPorIdExistente() {
        Rol rol = new Rol(1L, "ADMIN");

        // Define el comportamiento del mock: cuando se llame a findById() con 1L, devuelve un Optional con el rol.
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        // Llama al método obtenerRolPorId() del servicio.
        Rol resultado = rolService.obtenerRolPorId(1L);

        // Verifica que el rol devuelto no sea nulo y que tenga el nombre esperado.
        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombre());
    }

    @Test
    public void testObtenerRolPorNombreExistente() {
        Rol rol = new Rol(1L, "ADMIN");

        // Define el comportamiento del mock: cuando se llame a findByNombre() con "ADMIN", devuelve un Optional con el rol.
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));

        // Llama al método obtenerRolPorNombre() del servicio.
        Rol resultado = rolService.obtenerRolPorNombre("ADMIN");

        // Verifica que el rol devuelto no sea nulo y que tenga el ID esperado.
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }
}

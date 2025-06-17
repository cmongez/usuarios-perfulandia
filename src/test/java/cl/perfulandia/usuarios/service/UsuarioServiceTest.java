package cl.perfulandia.usuarios.service;

import cl.perfulandia.usuarios.model.Usuario;
import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.repository.UsuarioRepository;
import cl.perfulandia.usuarios.repository.RolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {

    // Inyecta el servicio de Usuario que será probado
    @Autowired
    private UsuarioService usuarioService;

    // Crea mocks de los repositorios utilizados por el servicio
    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private RolRepository rolRepository;

    @Test
    public void testListarUsuarios() {
        // Crea un usuario ficticio para simular datos en la base de datos
        Usuario usuario = new Usuario();
        usuario.setUsername("juan");

        // Define el comportamiento del mock: findAll() devuelve una lista con un solo usuario
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        // Llama al método listarUsuarios() del servicio
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Verifica que la lista contenga exactamente un usuario con el nombre esperado
        assertEquals(1, usuarios.size());
        assertEquals("juan", usuarios.get(0).getUsername());
    }

    @Test
    public void testRegistrarUsuarioConRol() {
        // Crea un usuario sin rol
        Usuario usuario = new Usuario();
        usuario.setUsername("ana");

        // Crea un rol ficticio
        Rol rol = new Rol(1L, "ADMIN");

        // Define el comportamiento del mock: el rol existe y el usuario es guardado exitosamente
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        // Llama al método registrarUsuario() del servicio
        Usuario registrado = usuarioService.registrarUsuario(usuario, "ADMIN");

        // Verifica que el usuario haya sido registrado correctamente
        assertNotNull(registrado);
    }

    @Test
    public void testLoginCorrecto() {
        // Crea un usuario con credenciales correctas y activo
        Usuario usuario = new Usuario();
        usuario.setUsername("luis");
        usuario.setPassword("1234");
        usuario.setActivo(true);

        // Define el comportamiento del mock: el usuario es encontrado en la base de datos
        when(usuarioRepository.findByUsername("luis")).thenReturn(Optional.of(usuario));

        // Llama al método login() con credenciales válidas
        Usuario logueado = usuarioService.login("luis", "1234");

        // Verifica que el login haya sido exitoso y que el usuario sea el esperado
        assertEquals("luis", logueado.getUsername());
    }

    @Test
    public void testObtenerUsuarioPorId() {
        // Crea un usuario con ID 1
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("maria");

        // Define el comportamiento del mock: findById devuelve el usuario
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Llama al método obtenerUsuarioPorId()
        Usuario encontrado = usuarioService.obtenerUsuarioPorId(1L);

        // Verifica que el usuario recuperado sea el esperado
        assertEquals("maria", encontrado.getUsername());
    }

    @Test
    public void testObtenerUsuariosPorRol() {
        // Crea un usuario con rol "CLIENTE"
        Usuario usuario = new Usuario();
        usuario.setUsername("cliente");

        // Define el comportamiento del mock: el repositorio retorna una lista con el usuario
        when(usuarioRepository.findByRol_Nombre("CLIENTE")).thenReturn(List.of(usuario));

        // Llama al método obtenerUsuariosPorRol()
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol("CLIENTE");

        // Verifica que se obtenga al menos un usuario
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testActualizarUsuario() {
        // Usuario existente con ID 1
        Usuario existente = new Usuario();
        existente.setId(1L);

        // Nuevos datos a actualizar
        Usuario nuevosDatos = new Usuario();
        nuevosDatos.setUsername("nuevo");
        nuevosDatos.setEmail("nuevo@email.com");
        nuevosDatos.setNombre("Nuevo");
        nuevosDatos.setApellido("Apellido");
        nuevosDatos.setRut("12345678-9");
        nuevosDatos.setDireccion("Calle 123");

        // Define el comportamiento del mock: se encuentra el usuario y se guarda con los nuevos datos
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(existente)).thenReturn(existente);

        // Llama al método actualizarUsuario()
        Usuario actualizado = usuarioService.actualizarUsuario(1L, nuevosDatos);

        // Verifica que los datos del usuario hayan sido actualizados correctamente
        assertEquals("nuevo", actualizado.getUsername());
        assertEquals("12345678-9", actualizado.getRut());
    }

    @Test
    public void testEliminarUsuario() {
        // Crea un usuario con ID 1
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        // Define el comportamiento del mock: se encuentra el usuario y se elimina correctamente
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        // Llama al método eliminarUsuario()
        usuarioService.eliminarUsuario(1L);

        // Verifica que el método delete() del repositorio haya sido llamado una vez
        verify(usuarioRepository, times(1)).delete(usuario);
    }
}

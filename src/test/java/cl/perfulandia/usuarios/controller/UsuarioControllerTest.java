package cl.perfulandia.usuarios.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.perfulandia.usuarios.dto.LoginRequest;
import cl.perfulandia.usuarios.dto.UsuarioRegistroRequest;
import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.model.Usuario;
import cl.perfulandia.usuarios.service.UsuarioService;

@WebMvcTest(UsuarioController.class) // Indica que se está probando el controlador de Usuario
public class UsuarioControllerTest {

        @Autowired
    private MockMvc mockMvc; // Proporciona una manera de realizar peticiones HTTP en las pruebas

    @MockBean
    private UsuarioService usuarioService; // Crea un mock del servicio de Usuario

    @Autowired
    private ObjectMapper objectMapper; // Se usa para convertir objetos Java a JSON y viceversa

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Configura un objeto Usuario de ejemplo antes de cada prueba
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("juan");
        usuario.setPassword("1234");
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setRut("12345678-9");
        usuario.setDireccion("Calle 1");
        usuario.setActivo(true);
        usuario.setRol(new Rol(1L, "ADMIN"));
    }

        @Test
    public void testListarUsuarios() throws Exception {
        // Define el comportamiento del mock: cuando se llame a listarUsuarios(), devuelve una lista con un Usuario
        when(usuarioService.listarUsuarios()).thenReturn(List.of(usuario));

        // Realiza una petición GET a /usuarios y verifica que la respuesta sea correcta
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("juan"));
    }

    @Test
    public void testRegistrarUsuario() throws Exception {
        UsuarioRegistroRequest request = new UsuarioRegistroRequest();
        request.setUsuario(usuario);
        request.setNombreRol("ADMIN");

        // Define el comportamiento del mock: registrarUsuario devuelve el usuario registrado
        when(usuarioService.registrarUsuario(any(), eq("ADMIN"))).thenReturn(usuario);

        // Realiza una petición POST a /usuarios/auth/registro con un body JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/usuarios/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("juan"));
    }

        @Test
    public void testLoginUsuario() throws Exception {
        LoginRequest login = new LoginRequest();
        login.setUsername("juan");
        login.setPassword("1234");

        // Define el comportamiento del mock: login devuelve el usuario encontrado
        when(usuarioService.login("juan", "1234")).thenReturn(usuario);

        // Realiza una petición POST a /usuarios/auth/login con el login JSON y verifica la respuesta
        mockMvc.perform(post("/usuarios/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("juan"));
    }

    @Test
    public void testObtenerUsuarioPorId() throws Exception {
        // Define el comportamiento del mock: obtenerUsuarioPorId devuelve un usuario con id 1
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        // Realiza una petición GET a /usuarios/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
 @Test
    public void testObtenerUsuariosPorRol() throws Exception {
        // Define el comportamiento del mock: obtenerUsuariosPorRol devuelve una lista con el usuario
        when(usuarioService.obtenerUsuariosPorRol("ADMIN")).thenReturn(List.of(usuario));

        // Realiza una petición GET a /usuarios/rol/ADMIN y verifica que la respuesta sea correcta
        mockMvc.perform(get("/usuarios/rol/ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rol.nombre").value("ADMIN"));
    }

    @Test
    public void testActualizarUsuario() throws Exception {
        // Define el comportamiento del mock: actualizarUsuario devuelve el usuario actualizado
        when(usuarioService.actualizarUsuario(eq(1L), any())).thenReturn(usuario);

        // Realiza una petición PUT a /usuarios/1 con un body JSON y verifica que la respuesta sea correcta
        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testEliminarUsuario() throws Exception {
        // Define el comportamiento del mock: no hace nada al eliminar
        doNothing().when(usuarioService).eliminarUsuario(1L);

        // Realiza una petición DELETE a /usuarios/1 y verifica que la respuesta sea correcta
        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());

        // Verifica que el método eliminarUsuario() del servicio se haya llamado exactamente una vez con el id 1
        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }

}

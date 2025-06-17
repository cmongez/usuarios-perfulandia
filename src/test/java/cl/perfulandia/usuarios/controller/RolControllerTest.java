package cl.perfulandia.usuarios.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.perfulandia.usuarios.model.Rol;
import cl.perfulandia.usuarios.service.RolService;

@WebMvcTest(RolController.class) // Indica que se está probando el controlador de Rol
public class RolControllerTest {
    @Autowired
    private MockMvc mockMvc; // Permite realizar peticiones HTTP simuladas

    @MockBean
    private RolService rolService; // Crea un mock del servicio de Rol

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON y viceversa

    private Rol rol;

    @BeforeEach
    void setUp() {
        // Configura un objeto Rol de ejemplo antes de cada prueba
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ADMIN");
    }

    @Test
    public void testObtenerTodosLosRoles() throws Exception {
        // Define el comportamiento del mock: cuando se llame a obtenerTodosLosRoles(), devuelve una lista con un Rol
        when(rolService.obtenerTodosLosRoles()).thenReturn(List.of(rol));

        // Realiza una petición GET a /roles y verifica que la respuesta sea correcta
        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica que el primer elemento tenga id 1
                .andExpect(jsonPath("$[0].nombre").value("ADMIN")); // Verifica que el primer elemento tenga nombre "ADMIN"
    }
     @Test
    public void testObtenerRolPorId() throws Exception {
        // Define el comportamiento del mock: cuando se llame a obtenerRolPorId() con 1, devuelve el objeto Rol
        when(rolService.obtenerRolPorId(1L)).thenReturn(rol);

        // Realiza una petición GET a /roles/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.nombre").value("ADMIN")); // Verifica que el nombre del objeto devuelto sea "ADMIN"
    }

    @Test
    public void testCrearRol() throws Exception {
        // Define el comportamiento del mock: cuando se llame a registrarRol(), devuelve el objeto Rol
        when(rolService.registrarRol(any(Rol.class))).thenReturn(rol);

        // Realiza una petición POST a /roles con el objeto Rol en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol))) // Convierte el objeto Rol a JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.nombre").value("ADMIN")); // Verifica que el nombre del objeto devuelto sea "ADMIN"
    }
}

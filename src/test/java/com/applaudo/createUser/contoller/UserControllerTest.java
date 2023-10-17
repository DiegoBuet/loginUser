package com.applaudo.createUser.contoller;

import com.applaudo.createUser.model.entity.User;
import com.applaudo.createUser.repository.UserRepository;
import com.applaudo.createUser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;
  /*  @Mock
    private UserService userService;*/

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private UserRepository userRepository;

/*    @InjectMocks
    private UserController userController;*/


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }




    @Test
    public void testListUsers() throws Exception {
        // Configura el objeto User que esperas recibir en la respuesta
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPhoneNumber("+503 12345678");
        user.setPassword("password123");

        // Configura el comportamiento del servicio mock
        Mockito.when(userService.listUsers()).thenReturn(List.of(user));

        // Realiza una solicitud GET al endpoint /list
        mockMvc.perform(MockMvcRequestBuilders.get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("johndoe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("+503 12345678"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value("password123"));
    }

/*
    @Test
    public void testSaveUser_ValidUser() throws Exception {
        // Crear un usuario válido
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("12345678");
        user.setPassword("securePassword");

        // Configura el comportamiento del servicio mock
        when(userService.findUserByEmail(user.getEmail())).thenReturn(null); // Usuario no existente

        // Realiza una solicitud POST al endpoint /create con el usuario válido
        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successfully"));

        // Verifica que el servicio haya guardado el usuario
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    public void testSaveUser_UserWithEmailExists() throws Exception {
        // Crear un usuario con correo electrónico existente
        User user = new User();
        user.setEmail("john.doe@example.com");

        // Configura el comportamiento del servicio mock para un usuario existente
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);

        // Realiza una solicitud POST al endpoint /create con el usuario existente
        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email already registered"));

        // Verifica que el servicio no haya guardado el usuario
        verify(userService, never()).saveUser(user);
    }*/
/*        @Test
        public void testSaveUser_ValidUser() {
            User user = new User();
            when(userService.findUserByEmail(any())).thenReturn(null);
            when(userService.saveUser(any(User.class)).thenReturn(user);

            ResponseEntity<String> response = userController.saveUser(user);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("User created successfully", response.getBody());
        }*/

/*    @Test
    public void testSaveUser_DuplicateEmail() {
        User user = new User();
        when(userService.findUserByEmail(any())).thenReturn(user);

        ResponseEntity<String> response = userController.saveUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already registered", response.getBody());
    }*/
}
package com.applaudo.createUser.contoller;

import com.applaudo.createUser.model.entity.User;
import com.applaudo.createUser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("johndoe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("+503 12345678"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value("password123"));
    }


/*    @Test
    void listUsers() {
        ResponseEntity<List> response = testRestTemplate.getForEntity("/list", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        List<User> users = response.getBody();

        assertEquals(1, users.size());

        User user = users.get(0);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }*/


/*
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;


    @BeforeEach
    void setUp() {
  *//*      restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);*//*
    }
    @Test
    void showUserRegistrationForm() {
    }

    @Test
    void saveUser() {
    }

*//*    @Test
    void listUsers() {
        ResponseEntity<User[]> response = testRestTemplate.getForEntity("/list", User[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        List<User> users = Arrays.asList(response.getBody());

        System.out.println(users.size());
    }*//*

*//*    @Test
    void listUsers() {
        ResponseEntity<List<User>> response = testRestTemplate.getForEntity("/api/users/list", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        List<User> users = response.getBody();

        System.out.println(users.size());
    }*//*

    @Test
    void listUsers() {
*//*        ResponseEntity<List<User>> response = testRestTemplate.exchange(
                "/api/users/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<User> users = response.getBody();

        System.out.println(users.size());*//*
    }

    @Test
    void testListUsers() {
    }*/
}
package com.applaudo.createUser.controller;

import com.applaudo.createUser.model.entity.User;
import com.applaudo.createUser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("Paco")
                .lastName("Ravana")
                .email("Paco@ravana.com")
                .phoneNumber("45686548")
                .password("q111")
                .build();
    }

    @Test
    void saveUser_ValidUser() throws Exception {
        User postUser = User.builder()
                .id(1L)
                .firstName("Paco")
                .lastName("Ravana")
                .email("Paco@ravana.com")
                .phoneNumber("45686548")
                .password("q111")
                .build();

        when(userService.saveUser(postUser)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testShowEditForm_UserExists() throws Exception {
        Mockito.when(userService.findUserById(1L)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testListUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Juan");
        user.setLastName("Lopez");
        user.setEmail("juan@lopez.com");
        user.setPhoneNumber("+503 12345678");
        user.setPassword("q123");

        Mockito.when(userService.listUsers()).thenReturn(List.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Lopez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("juan@lopez.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("+503 12345678"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value("q123"));
    }

    @Test
    void deleteUser() throws Exception {
        Long userIdToDelete = 1L;

        doNothing().when(userService).deleteUser(userIdToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete("/" + userIdToDelete))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void showEditForm_UserNotFound() throws Exception {
        Long userId = 2L;

        Mockito.when(userService.findUserById(userId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/" + userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void showEditForm_UserExistsWithFormattedPhoneNumber() throws Exception {
        Long userId = 1L;

        Mockito.when(userService.findUserById(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/edit/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Paco"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Ravana"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("Paco@ravana.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("45686548"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("q111"));
    }

    @Test
    void updateUser_ValidUpdate() throws Exception {
        Long userId = 1L;
        User updatedUser = User.builder()
                .id(userId)
                .firstName("Pedro")
                .lastName("Picapiedra")
                .email(user.getEmail())
                .phoneNumber("78901234")
                .password("q231")
                .build();

        when(userService.findUserById(userId)).thenReturn(user);

        when(userService.updateUser(any())).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User with ID 1 has been updated"));
    }

    @Test
    void updateUser_InvalidEmailModification() throws Exception {
        Long userId = 1L;
        User updatedUser = User.builder()
                .id(userId)
                .firstName("Pedro")
                .lastName("Picapiedra")
                .email("newemail@example.com")
                .phoneNumber("78901234")
                .password("q231")
                .build();

        when(userService.findUserById(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email cannot be modified"));
    }

    @Test
    void deleteUser_ValidUser() throws Exception {
        Long userIdToDelete = 1L;

        doNothing().when(userService).deleteUser(userIdToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete("/" + userIdToDelete))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveUser_InvalidEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Juan\",\"lastName\":\"Valdivia\",\"email\":\"invalid_email\",\"phoneNumber\":\"78901234\",\"password\":\"q123\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid email format"));
    }

    @Test
    void saveUser_InvalidPhoneNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@example.com\",\"phoneNumber\":\"invalid_phone\",\"password\":\"password123\"}"))
                .andExpect(MockMvcResultMatchers.content().string("Invalid phone number"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveUser_DuplicateEmail() throws Exception {
        when(userService.findUserByEmail("juan@valdivia.com")).thenReturn(User.builder().id(1L).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Juan\",\"lastName\":\"Valdivia\",\"email\":\"juan@valdivia.com\",\"phoneNumber\":\"78901234\",\"password\":\"q123\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email already registered"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

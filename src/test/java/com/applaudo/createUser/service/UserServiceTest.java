package com.applaudo.createUser.service;

import com.applaudo.createUser.model.entity.User;
import com.applaudo.createUser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
/*    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;*/

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .id(1L)
                .firstName("Paco")
                .lastName("Ravana")
                .email("Paco@ravana.com")
                .phoneNumber("45686548")
                .password("q111")
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Geronimo")
                .lastName("Gutierrez")
                .email("geronimo@gutierrez.com")
                .phoneNumber("12345678")
                .password("q123")
                .build();

        User user3 = User.builder()
                .id(3L)
                .firstName("Veronica")
                .lastName("Altamirano")
                .email("veronica@altamirano.com")
                .phoneNumber("98765432")
                .password("q111")
                .build();
        List<User> userList = Arrays.asList(user1, user2, user3);

        when(userRepository.findAll()).thenReturn(userList);
    }

    @Test
    public void testListUsers() {
        // Llama al método listUsers del servicio
        List<User> users = userService.listUsers();

        // Verifica que la lista de usuarios no esté vacía
        assertEquals(3, users.size()); // Debe haber 3 usuarios en la lista
    }
    @Test
    public void testSaveUser() {
        User userToSave = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("12345678")
                .password("test123")
                .build();

        when(userRepository.save(userToSave)).thenReturn(userToSave);
        User savedUser = userService.saveUser(userToSave);
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("john.doe@example.com", savedUser.getEmail());

    }

    @Test
    public void testUpdateUser() {
        // Crea un usuario existente en la configuración de tu prueba
        User userToUpdate = User.builder()
                .id(1L) // Establece el ID del usuario que deseas actualizar
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .email("updated.email@example.com")
                .phoneNumber("12345678")
                .password("newPassword")
                .build();

        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        User updatedUser = userService.updateUser(userToUpdate);

        assertNotNull(updatedUser);
        assertEquals(userToUpdate.getId(), updatedUser.getId()); // Asegúrate de que el ID no cambie
        assertEquals("UpdatedFirstName", updatedUser.getFirstName()); // Asegúrate de que otros campos se actualicen correctamente
        assertEquals("UpdatedLastName", updatedUser.getLastName());
        assertEquals("updated.email@example.com", updatedUser.getEmail());
        // Agrega más aserciones según tus requisitos
    }

    @Test
    public void testFindUserById() {
        // Obtén un ID de usuario existente en tu configuración
        Long userIdToFind = 1L; // El ID del usuario que deseas encontrar
        User userToFind = User.builder()
                .id(userIdToFind)
                .firstName("FoundFirstName")
                .lastName("FoundLastName")
                .email("found.email@example.com")
                .phoneNumber("12345678")
                .password("test123")
                .build();

        when(userRepository.findById(userIdToFind)).thenReturn(java.util.Optional.ofNullable(userToFind));

        User foundUser = userService.findUserById(userIdToFind);

        assertNotNull(foundUser);
        assertEquals(userIdToFind, foundUser.getId());
        assertEquals("FoundFirstName", foundUser.getFirstName()); // Asegúrate de que otros campos se obtengan correctamente
        assertEquals("FoundLastName", foundUser.getLastName());
        assertEquals("found.email@example.com", foundUser.getEmail());

    }

}
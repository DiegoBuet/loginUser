package com.applaudo.createUser.service;

import com.applaudo.createUser.model.entity.User;
import com.applaudo.createUser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        List<User> userList = Arrays.asList(
                new User(1L, "Paco", "Ravana", "Paco@ravana.com", "45686548", "q111"),
                new User(2L, "Geronimo", "Gutierrez", "geronimo@gutierrez.com", "12345678", "q123"),
                new User(3L, "Veronica", "Altamirano", "veronica@altamirano.com", "98765432", "q111")
        );

        when(userRepository.findAll()).thenReturn(userList);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userList.get(0)));
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(userList.get(1));
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);
    }

    @Test
    void testListUsers() {
        List<User> users = userService.listUsers();
        assertEquals(3, users.size());
    }

    @Test
    void testSaveUser() {
        User userToSave = new User(null, "Jonatan", "Gonzales", "jonatan@gonzales.com", "12345678", "q123");
        when(userRepository.save(userToSave)).thenReturn(userToSave);
        User savedUser = userService.saveUser(userToSave);
        assertNotNull(savedUser);
        assertEquals("Jonatan", savedUser.getFirstName());
        assertEquals("Gonzales", savedUser.getLastName());
        assertEquals("jonatan@gonzales.com", savedUser.getEmail());
    }

    @Test
    void testUpdateUser() {
        User userToUpdate = new User(1L, "Dani", "Lo", "Dani@Lo.com", "12345678", "q123");
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);
        User updatedUser = userService.updateUser(userToUpdate);
        assertNotNull(updatedUser);
        assertEquals(userToUpdate.getId(), updatedUser.getId());
        assertEquals("Dani", updatedUser.getFirstName());
        assertEquals("Lo", updatedUser.getLastName());
        assertEquals("Dani@Lo.com", updatedUser.getEmail());
    }

    @Test
    void testFindUserById() {
        Long userIdToFind = 1L;
        User foundUser = userService.findUserById(userIdToFind);
        assertNotNull(foundUser);
        assertEquals(userIdToFind, foundUser.getId());
        assertEquals("Paco", foundUser.getFirstName());
        assertEquals("Ravana", foundUser.getLastName());
        assertEquals("Paco@ravana.com", foundUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        Long userIdToDelete = 1L;
        assertDoesNotThrow(() -> userService.deleteUser(userIdToDelete));
    }

    @Test
    void testFindUserByEmail_UserExists() {
        String emailToFind = "cocho@lopez.com";
        User userToFind = new User(1L, "cocho", "lopez", emailToFind, "12345678", "q123");
        when(userRepository.findByEmail(emailToFind)).thenReturn(userToFind);
        User foundUser = userService.findUserByEmail(emailToFind);
        assertNotNull(foundUser);
        assertEquals(emailToFind, foundUser.getEmail());
    }

    @Test
    void testFindUserByEmail_UserNotFound() {
        String nonExistentEmail = "nonexistent@email.com";
        assertNull(userService.findUserByEmail(nonExistentEmail));
    }
}

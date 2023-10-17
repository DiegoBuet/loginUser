package com.applaudo.createUser.repository;

import com.applaudo.createUser.model.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.cglib.core.Local;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        User user1 = User.builder()
                .firstName("Paco")
                .lastName("Ravana")
                .email("Paco@ravana.com")
                .phoneNumber("45686548")
                .password("q111")
                .build();

        User user2 = User.builder()
                .firstName("Geronimo")
                .lastName("Gutierrez")
                .email("geronimo@gutierrez.com")
                .phoneNumber("12345678")
                .password("q123")
                .build();

        User user3 = User.builder()
                .firstName("Veronica")
                .lastName("Altamirano")
                .email("veronica@altamirano.com")
                .phoneNumber("98765432")
                .password("q111")
                .build();

        // Agregar los usuarios a la base de datos
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);
    }

    @Test
    public void testFindEmailByFound() {
        // Simula el usuario que deseas encontrar
        String emailToFind = "geronimo@gutierrez.com";
        // Intenta encontrar el usuario por su dirección de correo electrónico
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(emailToFind));
        // Verifica si el usuario fue encontrado y si su dirección de correo electrónico coincide
        assertTrue(foundUser.isPresent());
        assertEquals(emailToFind, foundUser.get().getEmail());
    }

    @Test
    public void testFindEmailAndIdByExistingEmail() {
        // Correo electrónico que deseas buscar
        String emailToFind = "veronica@altamirano.com";

        // Intenta encontrar el usuario por su dirección de correo electrónico
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(emailToFind));

        // Verifica si el usuario fue encontrado
        assertTrue(foundUser.isPresent());

        // Verifica si el correo electrónico coincide
        assertEquals(emailToFind, foundUser.get().getEmail());

        // Obtiene el ID del usuario encontrado
        Long userId = foundUser.get().getId();
        // Verifica que el ID no sea nulo
        assertNotNull(userId);
        System.out.println("user id " + userId);

    }

    @Test
    public void testFindEmailByNotFound() {
        // Simula el usuario que deseas encontrar
        String emailToFind = "jose@cano.com";
        // Intenta encontrar el usuario por su dirección de correo electrónico
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(emailToFind));
        // Verifica si el usuario no fue encontrado (debe estar vacío)
        assertTrue(foundUser.isEmpty());
    }



}
package com.applaudo.createUser.repository;

import com.applaudo.createUser.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void testFindByEmail_UserFound() {

        User user = User.builder()
                .firstName("Geronimo")
                .lastName("Gutierrez")
                .email("geronimo@gutierrez.com")
                .phoneNumber("12345678")
                .password("q123")
                .build();
        testEntityManager.persist(user);

        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail("geronimo@gutierrez.com"));

        assertTrue(foundUser.isPresent());
        assertEquals("geronimo@gutierrez.com", foundUser.get().getEmail());
    }

    @Test
    public void testFindByEmail_UserNotFound() {

        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail("nonexistent@email.com"));

        assertTrue(foundUser.isEmpty());
    }

    @Test
    public void testFindByEmailAndId_UserFound() {

        User user = User.builder()
                .firstName("Veronica")
                .lastName("Altamirano")
                .email("veronica@altamirano.com")
                .phoneNumber("98765432")
                .password("q111")
                .build();
        testEntityManager.persist(user);

        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail("veronica@altamirano.com"));

        assertTrue(foundUser.isPresent());

        assertEquals("veronica@altamirano.com", foundUser.get().getEmail());

        assertNotNull(foundUser.get().getId());
    }
}

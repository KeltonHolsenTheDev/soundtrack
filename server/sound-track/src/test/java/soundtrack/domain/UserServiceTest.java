package soundtrack.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import soundtrack.data.UserRepository;
import soundtrack.models.AccessLevel;
import soundtrack.models.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void shouldAddValidUser() {
        User kelton = new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        kelton.setRoles(List.of("The Boss"));
        User mockOut = kelton;

        when(repository.addUser(kelton)).thenReturn(mockOut);

        Result<User> actual = service.add(kelton);
        assertTrue(actual.isSuccess());
        assertEquals(kelton, actual.getPayLoad());
    }

    @Test
    void shouldNotAddUserWithNoRoles() {
        User kelton = new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        User mockOut = kelton;

        when(repository.addUser(kelton)).thenReturn(mockOut);

        Result<User> actual = service.add(kelton);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddUserWithDuplicateEmail() {
        User kelton = new User("Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        kelton.setRoles(List.of("The Boss"));
        User mockOut = kelton;

        when(repository.addUser(kelton)).thenReturn(mockOut);
        when(repository.findAll()).thenReturn(List.of(kelton));

        Result<User> actual = service.add(kelton);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullUser() {
        Result<User> actual = service.add(null);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldUpdate() {
        User kelton = new User(1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        kelton.setRoles(List.of("The Boss"));
        User newKelton = kelton;
        newKelton.setPassword("artemisisthebestcat98");

        when(repository.update(newKelton)).thenReturn(true);

        Result<User> actual = service.update(newKelton);
        assertTrue(actual.isSuccess());
        assertEquals(newKelton, actual.getPayLoad());
    }

    @Test
    void shouldNotUpdateToDuplicateEmail() {
        User kelton = new User(1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        kelton.setRoles(List.of("The Boss"));
        User newKelton = kelton;
        newKelton.setPassword("artemisisthebestcat98");
        newKelton.setEmail("keltonholsen@gmail.com");

        when(repository.update(newKelton)).thenReturn(true);
        when(repository.findAll()).thenReturn(List.of(new User(2, "Kelton", "Holsen", "keltonholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish")));

        Result<User> actual = service.update(newKelton);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateToNoRoles() {
        User kelton = new User(1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        kelton.setRoles(List.of("The Boss"));
        User newKelton = kelton;
        newKelton.setPassword("artemisisthebestcat98");
        newKelton.setRoles(new ArrayList<>());

        when(repository.update(newKelton)).thenReturn(true);

        Result<User> actual = service.update(newKelton);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldDeleteIfPresent() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDeleteIfNotPresent() {
        when(repository.deleteById(1)).thenReturn(false);
        assertFalse(service.deleteById(1));
    }
}
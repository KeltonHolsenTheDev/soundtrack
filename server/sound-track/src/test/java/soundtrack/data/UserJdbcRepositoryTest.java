package soundtrack.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soundtrack.models.AccessLevel;
import soundtrack.models.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcRepositoryTest {

    @Autowired
    UserJdbcRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    private static List<User> defaults = List.of(
            new User(1, "Kelton", "Holsen", "keltonholsen@gmail.com", "555-555-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish"),
            new User(2, "Artemis", "Cat", "artemis@gmail.com", "777-777-7777", AccessLevel.ROLE_USER, "meowmeowmeowmeow")
    );

    @Test
    void shouldFindAll() {
        List<User> actual = repository.findAll();
        List<User> expected = defaults;
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        User expected = defaults.get(0);
        User actual = repository.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        User addMe = new User(3, "AyDy", "Burling", "aydy@gmail.com", "444-444-4444", AccessLevel.ROLE_ADMINISTRATOR, "passwordpassword");
        assertEquals(addMe, repository.addUser(addMe));
        assertEquals(repository.findAll().size(), 3);
    }

    @Test
    void shouldUpdate() {
        User newKelton = new User(1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfshfshfsh");
        assertTrue(repository.update(newKelton));
        assertEquals(newKelton, repository.findById(1));
    }

    @Test
    void shouldDelete() {
        //Artemis should not use sound equipment
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    @Test
    void userShouldHaveRoles() {
        User kelton = repository.findById(1);
        assertEquals(kelton.getRoles().get(0), "Sound Board");
    }

    @Test
    void shouldAddWithRoles() {
        User addMe = new User(3, "AyDy", "Burling", "aydy@gmail.com", "444-444-4444", AccessLevel.ROLE_ADMINISTRATOR, "passwordpassword");
        addMe.setRoles(List.of("Carpenter"));
        repository.addUser(addMe);
        User out = repository.findById(3);
        assertEquals(out.getRoles().get(0), "Carpenter");
    }

    @Test
    void shouldUpdateRoles() {
        User kelton = defaults.get(0);
        kelton.setRoles(List.of("Carpenter"));
        repository.update(kelton);
        User out = repository.findById(1);
        assertEquals(out.getRoles().get(0), "Carpenter");
    }

}
package soundtrack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import soundtrack.data.UserRepository;
import soundtrack.models.AccessLevel;
import soundtrack.models.User;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    UserRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldAdd() throws Exception {
        User user = new User( "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        user.setRoles(List.of("Carpenter"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(user);
        when(repository.addUser(user)).thenReturn(user);

        var request = post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldNotAddBad() throws Exception {
        User user = new User();
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(user);
        when(repository.addUser(user)).thenReturn(user);
        var request = post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        User user = new User( 1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        user.setRoles(List.of("Carpenter"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(user);
        when(repository.update(user)).thenReturn(true);

        var request = put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldNotUpdateWithConflict() throws Exception {
        //no ID
        User user = new User(  1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        user.setRoles(List.of("Carpenter"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(user);
        when(repository.update(user)).thenReturn(true);

        var request = put("/api/user/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void shouldGiveNotFoundIfNotFound() throws Exception {
        User user = new User( 1, "Kelton", "Holsen", "kholsen@gmail.com", "555-455-5555", AccessLevel.ROLE_ADMINISTRATOR, "swordfishfishfish");
        user.setRoles(List.of("Carpenter"));
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(user);
        when(repository.update(user)).thenReturn(false);

        var request = put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotUpdateIfBadRequest() throws Exception {
        User user = new User();
        user.setUserId(1);
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(user);
        when(repository.update(user)).thenReturn(true);
        var request = put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDoValidDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);
        var request = delete("/api/user/1");

        mvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldntDoValidntDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(false);
        var request = delete("/api/user/1");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

}
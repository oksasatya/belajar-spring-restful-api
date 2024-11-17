package restful.api.springboot.belajarspringrestfulapi.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.RegisterUserRequest;
import restful.api.springboot.belajarspringrestfulapi.model.UpdateUserRequest;
import restful.api.springboot.belajarspringrestfulapi.model.UserResponse;
import restful.api.springboot.belajarspringrestfulapi.model.WebResponse;
import restful.api.springboot.belajarspringrestfulapi.repository.UserRepository;
import restful.api.springboot.belajarspringrestfulapi.security.BCrypt;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("password");
        request.setName("test");

        mockMvc.perform(
                post("/api/users")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertEquals("OK",response.getData());
        });
    }

    @Test
    void testRegisterBadRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("");
        request.setPassword("");
        request.setName("");

        mockMvc.perform(
                post("/api/users")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testRegisterDuplicate() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("password",BCrypt.gensalt()));
        user.setName("test");
        userRepository.save(user);


        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("password");
        request.setName("test");

        mockMvc.perform(
                post("/api/users")
                        .accept("application/json")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserUnauthorized() throws Exception {
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","notfound")

        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserUnauthorizedTokenNotSend() throws Exception {
        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("password",BCrypt.gensalt()));
        user.setName("test");
        user.setToken("token");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000L);
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","token")

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNull(response.getErrors());
            assertEquals("test",response.getData().getUsername());
            assertEquals("test",response.getData().getName());
        });
    }

    @Test
    void getUserTokenExpired() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("password",BCrypt.gensalt()));
        user.setName("test");
        user.setToken("token");
        user.setTokenExpiredAt(System.currentTimeMillis() - 10000000L);
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN","token")

        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNotNull(response.getErrors());
        });
    }


    @Test
    void updateUserUnauthorized() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        mockMvc.perform(
                patch("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("password",BCrypt.gensalt()));
        user.setName("test");
        user.setToken("token");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000L);
        userRepository.save(user);
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("test1");
        request.setPassword("password");

        mockMvc.perform(
                patch("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN","token")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
            assertNull(response.getErrors());
            assertEquals("test1",response.getData().getName());
            assertEquals("test",response.getData().getUsername());
        });
    }
}
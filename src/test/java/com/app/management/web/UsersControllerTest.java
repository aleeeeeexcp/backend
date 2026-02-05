package com.app.management.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.management.bean.LoginParamsDto;
import com.app.management.bean.UsersDto;
import com.app.management.config.JwtGenerator;
import com.app.management.config.JwtInfo;
import com.app.management.constants.RoleType;
import com.app.management.model.Users;
import com.app.management.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private UsersService usersService;

    @SuppressWarnings("removal")
    @MockBean
    private JwtGenerator jwtGenerator;

    private UsersDto createUsersDto(String id, String username, String email, String password) {
        return UsersDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
    
    @SuppressWarnings("null")
    @Test
    void signUp_returnsAuthenticatedUserDto() throws Exception {
        UsersDto usersDto = createUsersDto("u1", "alice", "alice@example.com", "raw");

        when(jwtGenerator.generate(any(JwtInfo.class))).thenReturn("token123");

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usersDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceToken").value("token123"))
                .andExpect(jsonPath("$.userDto.username").value("alice"))
                .andExpect(jsonPath("$.userDto.email").value("alice@example.com"));
    }

    @SuppressWarnings("null")
    @Test
    void login_returnsAuthenticatedUserDto() throws Exception {
        LoginParamsDto loginParamsDto = LoginParamsDto.builder()
                .username("bob")
                .password("secret")
                .build();

        Users user = Users.builder()
                .id("u2")
                .username("bob")
                .email("bob@example.com")
                .roleType(RoleType.ADMIN)
                .build();

        when(usersService.login(loginParamsDto.getUsername(), loginParamsDto.getPassword())).thenReturn(user);
        when(jwtGenerator.generate(any(JwtInfo.class))).thenReturn("token456");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginParamsDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceToken").value("token456"))
                .andExpect(jsonPath("$.userDto.username").value("bob"))
                .andExpect(jsonPath("$.userDto.email").value("bob@example.com"))
                .andExpect(jsonPath("$.userDto.roleType").value("ADMIN"));
    }
}

package com.app.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.app.management.constants.RoleType;
import com.app.management.model.Users;
import com.app.management.repository.UsersRepository;
import com.app.management.service.exceptions.DuplicateInstanceException;
import com.app.management.service.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    private Users createUsers(String id, String username, String email, String password, RoleType roleType) {
        return Users.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .roleType(roleType)
                .build();
    }
    @SuppressWarnings("null")
    @Test
    void signUp_setsRoleAndEncodesPassword() throws Exception {
        Users user = createUsers("u1", "alice", "alice@example.com", "raw", null);

        when(usersRepository.existsByEmail("alice@example.com")).thenReturn(false);
        when(usersRepository.existsByUsername("alice")).thenReturn(false);
        when(passwordEncoder.encode("raw")).thenReturn("encoded");

        usersService.signUp(user);

        verify(usersRepository).save(user);
        assertEquals("encoded", user.getPassword());
        assertEquals(RoleType.USER, user.getRoleType());
        assertEquals("alice", user.getUsername());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void signUp_throwsWhenDuplicateEmail() {
        Users user = createUsers(null, "alice", "alice@example.com", "raw", null);
        when(usersRepository.existsByEmail("alice@example.com")).thenReturn(true);

        assertThrows(DuplicateInstanceException.class, () -> usersService.signUp(user));
    }

    @Test
    void login_returnsUserWhenPasswordMatches() throws Exception {
        Users user = createUsers("u1", "alice", null, "encoded", null);
        when(usersRepository.findByUsername("alice")).thenReturn(user);
        when(passwordEncoder.matches("raw", "encoded")).thenReturn(true);

        Users result = usersService.login("alice", "raw");

        assertNotNull(result);
        assertEquals("u1", result.getId());
        assertEquals("alice", result.getUsername());
    }

    @Test
    void login_throwsWhenInvalidPassword() {
        Users user = createUsers("u1", "alice", null, "encoded", null); 

        when(usersRepository.findByUsername("alice")).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidParameterException.class, () -> usersService.login("alice", "raw"));
    }

    @Test
    void getAllUsers_returnsListOfUsers() {
        List<Users> users = List.of(
                createUsers("u1", "alice", "alice@example.com", "pass1", RoleType.USER),
                createUsers("u2", "bob", "bob@example.com", "pass2", RoleType.USER)
        );
        when(usersRepository.findByRoleType(RoleType.USER)).thenReturn(users);

        List<Users> result = usersService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("u1", result.get(0).getId());
        assertEquals("u2", result.get(1).getId());
    }

    @Test
    void deleteUsers_deletesById() {
        usersService.deleteUsers("u1");
        verify(usersRepository).deleteById("u1");
    }
}

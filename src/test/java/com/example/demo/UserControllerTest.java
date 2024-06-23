package com.example.demo;

import com.example.demo.Controller_jwt.UserController;
import com.example.demo.User.Entity.Rol;
import com.example.demo.User.Entity.User;
import com.example.demo.User.Jwt.JwtProvider;
import com.example.demo.User.Login.RolName;
import com.example.demo.User.Service.RolService;
import com.example.demo.User.Service.UserService;
import com.example.demo.User.Util.ValidEmail;
import com.example.demo.dto.JwtDto;
import com.example.demo.dto.Mensaje;
import com.example.demo.dto.NewUser;
import com.example.demo.dto.loginUser;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RolService rolService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserService userService;

    @Mock
    private ValidEmail validEmail;

    @InjectMocks
    private UserController userController;

    private static final Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNuevo_ValidUser() {
        NewUser newUser = new NewUser(null, "John Doe", "johndoe", "password", "johndoe@example.com", "user", null, null, null, null, null, null, false);

        try {
            when(ValidEmail.isValidEmail(anyString())).thenReturn(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  when(userService.loadUserByMail(anyString())).thenReturn(false);
        when(userService.loadUserByUsername(anyString())).thenReturn(false);
        when(rolService.getRolByName(RolName.ROLE_USER)).thenReturn(Optional.of(new Rol(RolName.ROLE_USER)));

        ResponseEntity<?> response = userController.nuevo(newUser, mock(BindingResult.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User Save", ((Mensaje) response.getBody()).getMensaje());
    }

    @Test
    public void testNuevo_ExistingEmail() {
        NewUser newUser = new NewUser(null, "John Doe", "johndoe", "password", "johndoe@example.com", "user", null, null, null, null, null, null, false);

        try {
            when(ValidEmail.isValidEmail(anyString())).thenReturn(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   when(userService.loadUserByMail(anyString())).thenReturn(true);

        ResponseEntity<?> response = userController.nuevo(newUser, mock(BindingResult.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("That Mail already exists", ((Mensaje) response.getBody()).getMensaje());
    }

    @Test
    public void testNuevo_ExistingUsername() {
        NewUser newUser = new NewUser(null, "John Doe", "johndoe", "password", "johndoe@example.com", "user", null, null, null, null, null, null, false);

        try {
            when(ValidEmail.isValidEmail(anyString())).thenReturn(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   when(userService.loadUserByMail(anyString())).thenReturn(false);
        when(userService.loadUserByUsername(anyString())).thenReturn(true);

        ResponseEntity<?> response = userController.nuevo(newUser, mock(BindingResult.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("That name already exists", ((Mensaje) response.getBody()).getMensaje());
    }

    @Test
    public void testLogin_ValidCredentials() {
        loginUser loginUser = new loginUser("johndoe","user", "password");

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtProvider.generateToken(authentication)).thenReturn("generated_token");

        ResponseEntity<JwtDto> response = userController.login(loginUser, mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("generated_token", response.getBody().getToken());
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = Collections.singletonList(new User("John Doe", "johndoe", "password"));

        when(userService.getUsers()).thenReturn(userList);

        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    public void testFindById_ValidId() {
        UUID userId = UUID.randomUUID();
        User user = new User("John Doe", "johndoe", "password");
        user.setId(userId);

        when(userService.findById(userId)).thenReturn(user);

        ResponseEntity<?> response = userController.findByID(userId, mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testDeleteUser_ValidId() {
        UUID userId = UUID.randomUUID();

        ResponseEntity<?> response = userController.deleteUser(userId, mock(BindingResult.class));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("User Deleted", ((Mensaje) response.getBody()).getMensaje());
    }

    @Test
    public void testEditUser_ValidUser() {
        NewUser newUser = new NewUser(UUID.randomUUID(), "John Doe", "johndoe", "password", "admin", null, null, null, null, null, null, null, false);

        userService.actualizarRol(any(UUID.class));

        ResponseEntity<?> response = userController.editUser(newUser, mock(BindingResult.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User Updated", ((Mensaje) response.getBody()).getMensaje());
    }
}
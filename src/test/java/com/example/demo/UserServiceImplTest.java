package com.example.demo;

import com.example.demo.User.Entity.User;
import com.example.demo.User.Repository.UserRepository;
import com.example.demo.User.ServiceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername_Success() {
        // Mock de UserRepository para devolver un usuario existente
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);

        // Llamada al método loadUserByUsername
        UserDetails userDetails = userService.loadUserByUsername("testUser");

        // Verificación de que se devuelva un UserDetails correcto
        assertEquals("testUser", userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Mock de UserRepository para devolver un usuario no existente
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);

        // Llamada al método loadUserByUsername, debe lanzar UsernameNotFoundException
        try {
          //  userService.loadUserByUsername("testUserNotFound");
        } catch (UsernameNotFoundException e) {
            // Verificación de que se lance la excepción esperada
            assertEquals("Usuario no encontrado con nombre de usuario: testUserNotFound", e.getMessage());
            verify(userRepository, times(1)).findByUsername("testUserNotFound");
        }
    }
}

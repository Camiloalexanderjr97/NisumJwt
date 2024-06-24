package com.example.demo;

import com.example.demo.User.Entity.User;
import com.example.demo.User.Repository.UserRepository;
import com.example.demo.User.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsers_Success() {
        // Mock de UserRepository para devolver una lista de usuarios
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Llamada al método getUsers
        List<User> userList = userService.getUsers();

        // Verificación de que se devuelva la lista de usuarios correctamente
        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertEquals("user1", userList.get(0).getUsername());
        assertEquals("user2", userList.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testCrearUser_Success() {
        // Mock de UserRepository para verificar el método save
        User newUser = new User();
        newUser.setName("New User");
        newUser.setUsername("newuser");
        newUser.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Llamada al método crearUser
        User savedUser = userService.crearUser(newUser);

        // Verificación de que se devuelve el usuario creado correctamente
        assertNotNull(savedUser);
        assertEquals("New User", savedUser.getName());
        assertEquals("newuser", savedUser.getUsername());
        assertEquals("password", savedUser.getPassword());
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreated());
        assertNotNull(savedUser.getModified());
        assertNotNull(savedUser.getLastLogin());
        assertTrue(savedUser.isActive());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser_Success() {
        UUID userId = UUID.randomUUID();

        // Llamada al método deleteUser
        boolean result = userService.deleteUser(userId);

        // Verificación de que se llama al método deleteById del UserRepository con el ID correcto
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    // Agrega más pruebas según sea necesario para otros métodos de UserService
}

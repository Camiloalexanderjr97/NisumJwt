package com.example.demo;

import com.example.demo.User.Entity.Rol;
import com.example.demo.User.Service.RolService;
import com.example.demo.User.Util.CreateRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class CreateRolesTest {

    @Mock
    private RolService rolService;

    @InjectMocks
    private CreateRoles createRoles;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRun() {
        // Simular el método save de RolService para verificar que se guardan los roles correctamente

        // Llamar al método run de CreateRoles
        try {
            createRoles.run();
        } catch (Exception e) {
            fail("Error al ejecutar el método run: " + e.getMessage());
        }

        // Verificar que se llama dos veces al método save de RolService, una vez por cada rol
        verify(rolService, times(2)).save(any(Rol.class));
    }

    // Agrega más pruebas según sea necesario para otros escenarios o casos de uso específicos
}

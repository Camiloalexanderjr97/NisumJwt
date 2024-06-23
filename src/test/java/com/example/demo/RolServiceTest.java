package com.example.demo;


import com.example.demo.User.Entity.Rol;
import com.example.demo.User.Login.RolName;
import com.example.demo.User.Repository.RolRepository;
import com.example.demo.User.Service.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRolByName_Success() {
        // Mock de RolRepository para devolver un Rol existente
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setRolName(RolName.ROLE_USER);
        when(rolRepository.findByRolName(any(RolName.class))).thenReturn(Optional.of(rol));

        // Llamada al método getRolByName
        Optional<Rol> foundRol = rolService.getRolByName(RolName.ROLE_USER);

        // Verificación de que se devuelva el Rol correcto
        assertEquals(1L, foundRol.get().getId());
        assertEquals(RolName.ROLE_USER, foundRol.get().getRolName());
        verify(rolRepository, times(1)).findByRolName(RolName.ROLE_USER);
    }

    @Test
    public void testSaveRol_Success() {
        // Mock de RolRepository para verificar el método save
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setRolName(RolName.ROLE_ADMIN);

        // Llamada al método save
        rolService.save(rol);

        // Verificación de que se llama al método save de RolRepository una vez
        verify(rolRepository, times(1)).save(rol);
    }
}

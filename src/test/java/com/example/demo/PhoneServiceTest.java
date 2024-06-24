package com.example.demo;

import com.example.demo.User.Entity.Phone;
import com.example.demo.User.Repository.PhoneRepository;
import com.example.demo.User.Service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneService phoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPhoneByNumber_Success() {
        // Mock de PhoneRepository para devolver un teléfono existente
        Phone phone = new Phone();
        phone.setNumber("123456789");
        when(phoneRepository.findByNumber(anyString())).thenReturn(phone);

        // Llamada al método getPhoneByNumber
        Phone foundPhone = phoneService.getPhoneByNumber("123456789");

        // Verificación de que se devuelva el teléfono correcto
        assertEquals("123456789", foundPhone.getNumber());
        verify(phoneRepository, times(1)).findByNumber("123456789");
    }

    @Test
    public void testSavePhone_Success() {
        // Mock de PhoneRepository para verificar el método save
        Phone phone = new Phone();
        phone.setNumber("987654321");

        // Llamada al método save
        phoneService.save(phone);

        // Verificación de que se llama al método save de PhoneRepository una vez
        verify(phoneRepository, times(1)).save(phone);
    }
}

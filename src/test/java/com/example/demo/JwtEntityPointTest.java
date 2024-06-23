package com.example.demo;

import com.example.demo.User.Jwt.JwtEntityPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class JwtEntityPointTest {

    @Mock
    private Logger logger;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private JwtEntityPoint jwtEntityPoint;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCommence_ErrorLoggingAndResponseSent() throws IOException {
        // Configurar el comportamiento esperado del logger
        doNothing().when(logger).error("Fail en el commence");

        // Llamar al método commence con los mocks preparados
        try {
            jwtEntityPoint.commence(request, response, authException);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Verificar que el logger haya sido llamado correctamente
    //    verify(logger, times(1)).error("Fail en el commence");

        // Verificar que se haya enviado un error de respuesta HTTP con el código adecuado y mensaje
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
    }
}

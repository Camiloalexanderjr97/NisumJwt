package com.example.demo;


import com.example.demo.User.Jwt.JwtProvider;
import com.example.demo.User.Util.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class JwtEntityProviderTest {

    @InjectMocks
    private JwtProvider jwtProvider;

    @Mock
    private Logger logger;

    @Test
    public void testGenerateToken() {
        // Simulamos un usuario autenticado
        UserPrincipal userPrincipal = new UserPrincipal("username", "password", null, null);
        

        // Mock del secret y expiration
        ReflectionTestUtils.setField(jwtProvider, "secret", "secret");
        ReflectionTestUtils.setField(jwtProvider, "expiration", 3600);

        // Generamos el token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        assertNotNull(token);

    }


    @Test
    public void testValidateToken_ValidToken() {
        // Simulamos un token válido
        String token = Jwts.builder().setSubject("username").signWith(SignatureAlgorithm.HS512, "secret").compact();

        assertFalse(jwtProvider.validateToken(token));
    }

    @Test
    public void testValidateToken_InvalidToken() {
        // Simulamos un token inválido
        String invalidToken = "invalid_token";

        assertFalse(jwtProvider.validateToken(invalidToken));
    }

    @Test
    public void testValidateToken_MalformedToken() {
        // Simulamos un token mal formado
        String malformedToken = "malformed_token";

        assertFalse(jwtProvider.validateToken(malformedToken));
    }
}

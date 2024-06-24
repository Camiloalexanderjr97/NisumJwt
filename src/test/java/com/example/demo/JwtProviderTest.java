package com.example.demo;

import com.example.demo.User.Jwt.JwtProvider;
import com.example.demo.User.Util.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JwtProviderTest {

    private static final String SECRET = "testSecret";
    private static final int EXPIRATION = 3600; // 1 hour in seconds

    @Mock
    private Logger logger;

    @InjectMocks
    private JwtProvider jwtProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtProvider = new JwtProvider();
    }


    @Test
    public void testValidateToken_InvalidToken() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtProvider.validateToken(invalidToken);

        assertFalse(isValid);
       // verify(logger).error("token mal formado");
    }

    // Helper method to create a valid token for testing purposes
    private String createValidToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // Stub class to mock Authentication for testing
    private static class UserAuthenticationStub implements Authentication {

        private final UserPrincipal userPrincipal;

        public UserAuthenticationStub(UserPrincipal userPrincipal) {
            this.userPrincipal = userPrincipal;
        }

        @Override
        public String getName() {
            return userPrincipal.getUsername();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return userPrincipal.getAuthorities();
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return userPrincipal;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            // Not needed for testing
        }
    }
}

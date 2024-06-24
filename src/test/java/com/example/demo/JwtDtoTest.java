package com.example.demo;

import com.example.demo.dto.JwtDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtDtoTest {

    @Test
    public void testJwtDtoConstructorAndGetters() {
        // Crear roles (authorities)
        GrantedAuthority authority1 = new SimpleGrantedAuthority("ROLE_USER");
        GrantedAuthority authority2 = new SimpleGrantedAuthority("ROLE_ADMIN");
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(authority1);

        // Crear objeto JwtDto
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        String nombre = "John Doe";
        JwtDto jwtDto = new JwtDto(token, nombre, authorities);

        // Verificar que los valores se han asignado correctamente
        assertEquals(token, jwtDto.getToken());
        assertEquals("Bearer", jwtDto.getBearer());
        assertEquals(nombre, jwtDto.getNombre());
        assertEquals(authorities, jwtDto.getAuthorities());
    }

    @Test
    public void testJwtDtoSetters() {
        // Crear objeto JwtDto vacío
        JwtDto jwtDto = new JwtDto(null, null, null);

        // Asignar valores usando setters
        String token = "new.token.value";
        String nombre = "Jane Smith";
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER"));

        jwtDto.setToken(token);
        jwtDto.setBearer("CustomBearer");
        jwtDto.setNombre(nombre);
        jwtDto.setAuthorities(authorities);

        // Verificar que los valores se han asignado correctamente
        assertEquals(token, jwtDto.getToken());
        assertEquals("CustomBearer", jwtDto.getBearer());
        assertEquals(nombre, jwtDto.getNombre());
        assertEquals(authorities, jwtDto.getAuthorities());
    }
}

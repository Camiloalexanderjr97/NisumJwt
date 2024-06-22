package com.example.demo.User.Jwt;

import com.example.demo.User.Util.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.el.ELException;
import java.util.Date;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UserPrincipal user =  (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public String getNombreUserFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
            
        } catch (MalformedJwtException e) {
            logger.error("token mal formado");

        } catch (UnsupportedJwtException e) {
            logger.error("token no soportado");

        } catch (ELException e) {
            logger.error("token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("token vacio");
        } catch (SignatureException e) {
            logger.error("fail en la firma");
        }
        return false;
    }

}

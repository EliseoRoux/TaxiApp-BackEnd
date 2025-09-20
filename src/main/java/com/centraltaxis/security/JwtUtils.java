package com.centraltaxis.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys; // Importamos la clase Keys
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct; // Importamos PostConstruct
import java.nio.charset.StandardCharsets; // Importamos StandardCharsets
import java.security.Key; // Importamos la clase Key
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecretString;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // --- Variable para la clave segura ---
    private Key key;

    // --- Este método se ejecuta una vez al iniciar la app ---
    // Convierte el string de la clave secreta en un objeto Key seguro.
    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtSecretString.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            logger.error("Firma del JWT inválida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("El token JWT ha expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("El token JWT no es compatible: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("La cadena de claims del JWT está vacía: {}", e.getMessage());
        }
        return false;
    }
}
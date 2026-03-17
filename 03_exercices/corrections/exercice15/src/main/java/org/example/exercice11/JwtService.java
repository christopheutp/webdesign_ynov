package org.example.exercice11;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // generation du cle qui seras utiliser pour generer nos token


    // methode pour generer un token  avec un nom d'utilisateur et une collection qui sera place dans le token
    public String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // ajout des claims dans le token
                .setSubject(username) // ajout du nom de l'utilisateur
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // expiration du token 2H
        // apres sa generation
                .signWith(key) // utilisation de la cle pour le cryptage du token
                .compact();
    }


    // methode pour verifier qu'un token est bon
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

package br.com.itau.challenge.security;

import br.com.itau.challenge.data.UserDetailsData;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class TokenManager implements Serializable {

    @Value("${itauchallenge.jwt.secret}")
    private String secret;

    @Value("${itauchallenge.jwt.expiration}")
    private long expirationInMillis;

    public String generateToken(Authentication authentication) {
        UserDetailsData userData = (UserDetailsData) authentication.getPrincipal();

        String token = JWT.create()
                .withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationInMillis))
                .sign(Algorithm.HMAC512(secret));

        return token;
    }

    public boolean isValid(String jwt) {
        try {
            JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmailFromToken(String jwt) {
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(jwt)
                .getSubject();
    }
}

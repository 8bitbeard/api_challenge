package br.com.itau.challenge.services;

import br.com.itau.challenge.exceptions.AuthenticationFailedException;
import br.com.itau.challenge.security.TokenManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AuthenticationService  {

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;

    public String authenticate(Authentication authenticationToken) {
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return tokenManager.generateToken(authentication);
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException();
        }
    }
}

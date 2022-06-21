package br.com.itau.challenge.services;

import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.UserAlreadyExistsException;
import br.com.itau.challenge.exceptions.UserNotFoundException;
import br.com.itau.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public User create(User user) {
        boolean emailInUse = userRepository.findByEmail(user.getEmail())
                .stream()
                .anyMatch(existingClient -> !existingClient.equals(user));

        if(emailInUse) {
            throw new UserAlreadyExistsException("O usuário informado já existe!");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User find(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
    }
}

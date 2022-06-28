package br.com.itau.challenge.services;

import br.com.itau.challenge.data.UserDetailsData;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.UserAlreadyExistsException;
import br.com.itau.challenge.exceptions.UserNotFoundException;
import br.com.itau.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@ComponentScan("br.com.itau.challenge.repositories.UserRepository")
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public User create(User user) {
        boolean emailInUse = userRepository.findByEmail(user.getEmail())
                .stream()
                .anyMatch(existingClient -> !existingClient.equals(user));
        boolean cpfInUse = userRepository.findByCpf(user.getCpf())
                .stream()
                .anyMatch(existingClient -> !existingClient.equals(user));

        if(emailInUse || cpfInUse) {
            log.error("Trying to create a new user with email or cpf already used: {}", user.getEmail());
            throw new UserAlreadyExistsException();
        }


        log.info("Creating a new user with email {}", user.getEmail());
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> list() {
        log.info("Listing all users");
        return userRepository.findAll();
    }

    public User find(String email) {
        log.info("Find a user by email {}", email);
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isEmpty()) {
            log.error("User by email {} not found", username);
            throw new UserNotFoundException();
        }

        log.info("Find a user by email {}", username);
        return new UserDetailsData(user);
    }
}

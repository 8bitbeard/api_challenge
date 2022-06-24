package br.com.itau.challenge.services;

import br.com.itau.challenge.data.UserDetailsData;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.UserAlreadyExistsException;
import br.com.itau.challenge.exceptions.UserNotFoundException;
import br.com.itau.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new UserDetailsData(user);
    }
}

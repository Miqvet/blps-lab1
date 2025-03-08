package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.User;
import itmo.blps.lab1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new RuntimeException("User with email " + email + " not found"));
    }
    public User findByUserId(UUID uuid){
        return userRepository.findById(uuid).orElseThrow(()
                -> new RuntimeException("User with id " + uuid + " not found"));
    }
    public boolean existsByUsername(String username) {
        return userRepository.findByEmail(username).isPresent();
    }
    public User save(User user) {
        return userRepository.save(user);
    }
}

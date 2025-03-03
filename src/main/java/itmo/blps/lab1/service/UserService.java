package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.User;
import itmo.blps.lab1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new RuntimeException("User with name " + username + " not found"));
    }
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    public User save(User user) {
        return userRepository.save(user);
    }
}

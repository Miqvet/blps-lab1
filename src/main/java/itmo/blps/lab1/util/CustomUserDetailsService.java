package itmo.blps.lab1.util;

import itmo.blps.lab1.entity.User;
import itmo.blps.lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(
            user.getName(),
            user.getPassword(),
            new ArrayList<>()
        );
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
} 
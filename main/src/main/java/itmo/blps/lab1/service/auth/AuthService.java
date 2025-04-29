package itmo.blps.lab1.service.auth;

import itmo.blps.lab1.dto.request.AuthRequest;
import itmo.blps.lab1.dto.request.RegisterRequest;
import itmo.blps.lab1.entity.User;
import itmo.blps.lab1.entity.enums.Role;
import itmo.blps.lab1.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User registerUser(RegisterRequest request) {
        if (userService.existsByUsername(request.getName())) {
            throw new IllegalArgumentException("User with username " + request.getName() + " already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.USER);
        return userService.save(user);
    }

    public String authenticateUser(AuthRequest request) {
        User user = userService.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Неверные учетные данные");
        }

        return jwtUtil.generateToken(user);
    }
} 
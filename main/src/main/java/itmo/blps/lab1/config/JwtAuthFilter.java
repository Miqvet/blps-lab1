package itmo.blps.lab1.config;

import itmo.blps.lab1.repository.UserRepository;
import itmo.blps.lab1.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtService; // Сервис для валидации и извлечения данных из токена
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//
//        System.out.println("зашли в jwt");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        System.out.println("прошли 1 этап");
//        final String jwt = authHeader.substring(7);
//        String userEmail = jwtService.extractUsername(jwt);
//
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            var user = userRepository.findByEmail(userEmail)
//                    .orElse(null);
//
//            System.out.println("прошли 2 этап");
//            if (user != null && jwtService.validateToken(jwt, user.getName())) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                user.getEmail(),
//                                null,
//                                user.getPrivileges().stream()
//                                        .map(Privilege::getName) // превращаем enum в String
//                                        .map(SimpleGrantedAuthority::new)
//                                        .toList()
//                        );
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                System.out.println("записали данные в контекст");
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        System.out.println("пошли на сл этап");
        filterChain.doFilter(request, response);
    }
}
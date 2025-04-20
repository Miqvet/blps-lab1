package itmo.blps.lab1.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test-auth")
@RequiredArgsConstructor
public class TestAuthorizationController {

    // Доступен всем, у кого есть BASIC привилегия
    @GetMapping("/basic")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> basicAccess() {
        return ResponseEntity.ok("Привет, у тебя есть BASIC доступ!");
    }

    // Доступен только тем, у кого есть SELECT_DATE привилегия
    @GetMapping("/select-date")
    @PreAuthorize("hasAuthority('SELECT_DATE')")
    public ResponseEntity<String> selectDateAccess() {
        return ResponseEntity.ok("Привет, у тебя есть SELECT_DATE доступ!");
    }

    // Доступен только тем, у кого есть ALL привилегия
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ALL')")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Привет, у тебя есть полный ALL доступ!");
    }

    // Можно использовать для дебага — показать текущего пользователя и его authorities
    @GetMapping("/whoami")
    public ResponseEntity<Map<String, Object>> whoAmI(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        return ResponseEntity.ok(response);
    }
}


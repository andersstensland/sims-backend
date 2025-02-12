package org.kristiania.smartinventorymanagementsystem.controllers;


import lombok.RequiredArgsConstructor;
import org.kristiania.smartinventorymanagementsystem.model.User;
import org.kristiania.smartinventorymanagementsystem.repository.UserRepository;
import org.kristiania.smartinventorymanagementsystem.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Simple DTOs for request/response
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        // Check if user exists
        if (userRepository.findByUsername(request.username).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        // Create user
        User user = User.builder()
                .username(request.username)
                .password(passwordEncoder.encode(request.password))
                .roles("ROLE_USER") // or whatever roles
                .build();
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // Class 'AuthRequest' is exposed outside its defined visibility scope
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        var userOpt = userRepository.findByUsername(request.username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        User user = userOpt.get();
        // Verify password
        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        // Generate token
        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // Basic DTOs
    record AuthRequest(String username, String password) {}
    record AuthResponse(String token) {}
}

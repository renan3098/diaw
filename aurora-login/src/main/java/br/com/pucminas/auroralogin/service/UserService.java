package br.com.pucminas.auroralogin.service;

import br.com.pucminas.auroralogin.dto.RegisterForm;
import br.com.pucminas.auroralogin.model.User;
import br.com.pucminas.auroralogin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private static final int TOKEN_VALIDADE_MINUTOS = 30;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User register(RegisterForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        return userRepository.save(user);
    }

    public void processPasswordRecovery(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return; // não revela se o email existe
        }

        User user = optionalUser.get();
        user.setResetToken(UUID.randomUUID().toString());
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(TOKEN_VALIDADE_MINUTOS));
        userRepository.save(user);

        emailService.sendPasswordRecoveryEmail(user.getEmail(), user.getResetToken());
    }

    public boolean isResetTokenValid(String token) {
        return userRepository.findByResetToken(token)
                .filter(user -> user.getResetTokenExpiry() != null)
                .filter(user -> user.getResetTokenExpiry().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<User> optionalUser = userRepository.findByResetToken(token);
        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();
        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        return true;
    }
}

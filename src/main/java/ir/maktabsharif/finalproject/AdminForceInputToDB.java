package ir.maktabsharif.finalproject;

import ir.maktabsharif.finalproject.entity.RegistrationStatus;
import ir.maktabsharif.finalproject.entity.Role;
import ir.maktabsharif.finalproject.entity.User;
import ir.maktabsharif.finalproject.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminForceInputToDB {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminForceInputToDB(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void applicationReady(ApplicationReadyEvent event) {
        if (userRepository.findByUsername("admin").isEmpty()){
            User newUser = new User();
            newUser.setUsername("admin");
            newUser.setPassword(passwordEncoder.encode("admin"));
            newUser.setRole(Role.ADMIN);
            newUser.setRegistrationStatus(RegistrationStatus.APPROVAL);
            userRepository.save(newUser);
        }
    }
}

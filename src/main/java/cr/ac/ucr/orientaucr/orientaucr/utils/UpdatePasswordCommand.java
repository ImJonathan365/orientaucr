package cr.ac.ucr.orientaucr.orientaucr.utils;

import cr.ac.ucr.orientaucr.orientaucr.domain.User;
import cr.ac.ucr.orientaucr.orientaucr.repository.IUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UpdatePasswordCommand {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void updatePasswords() {
        String[][] users = {
            {"ana.gomez@example.com", "password1"},
            {"carlos.perez@example.com", "password2"},
            {"laura.martinez@example.com", "password3"},
            {"miguel.lopez@example.com", "password4"},
            {"sofia.ramirez@example.com", "password5"},
            {"jorge.diaz@example.com", "password6"},
            {"maria.sanchez@example.com", "password7"},
            {"pedro.torres@example.com", "password8"},
            {"lucia.vega@example.com", "password9"},
            {"andres.mora@example.com", "password10"}
        };

        for (String[] user : users) {
            String email = user[0];
            String rawPassword = user[1];
            User existingUser = userRepository.findByUserEmail(email).orElse(null);
            if (existingUser != null) {
                String encodedPassword = passwordEncoder.encode(rawPassword);
                existingUser.setUserPassword(encodedPassword);
                userRepository.save(existingUser);
            }
        }
    }

}
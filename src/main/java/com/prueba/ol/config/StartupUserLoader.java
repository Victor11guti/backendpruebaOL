package com.prueba.ol.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prueba.ol.entity.Role;
import com.prueba.ol.entity.User;
import com.prueba.ol.repository.UserRepository;

@Configuration
public class StartupUserLoader {

    @Bean
    public CommandLineRunner loadDefaultUsers(UserRepository userRepository,
                                              PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@correo.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@correo.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMINISTRADOR);
                userRepository.save(admin);
            }

            if (userRepository.findByEmail("auxiliar@correo.com").isEmpty()) {
                User auxiliar = new User();
                auxiliar.setEmail("auxiliar@correo.com");
                auxiliar.setPassword(passwordEncoder.encode("auxiliar123"));
                auxiliar.setRole(Role.AUXILIAR_REGISTRO);
                userRepository.save(auxiliar);
            }
        };
    }
}


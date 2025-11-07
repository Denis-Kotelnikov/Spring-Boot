package ru.kotelnikov.MusicAppApplication.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kotelnikov.MusicAppApplication.entity.Role;
import ru.kotelnikov.MusicAppApplication.entity.User;
import ru.kotelnikov.MusicAppApplication.repository.RoleRepository;
import ru.kotelnikov.MusicAppApplication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/index", "/login", "/register", "/register/save", "/about").permitAll()
                        .requestMatchers("/songs", "/song-metrics", "/bonus",
                                "/add-song", "/add-metrics",
                                "/save-song", "/save-metrics",
                                "/update-song", "/update-metrics",
                                "/delete-song", "/delete-metrics").permitAll()
                        .requestMatchers("/manage-roles", "/assign-role").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/songs", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );
        return http.build();
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                adminRole = roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                userRole = roleRepository.save(userRole);
            }

            User admin = userRepository.findByUsername("admin");
            if (admin == null) {
                admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder().encode("admin123"));
                admin.setCreatedBy("system"); // Устанавливаем createdBy
                admin.setRoles(List.of(adminRole));
                userRepository.save(admin);
            }

            User denis = userRepository.findByUsername("denis23.06@mail.ru");
            if (denis == null) {
                denis = new User();
                denis.setUsername("denis23.06@mail.ru");
                denis.setPassword(passwordEncoder().encode("123")); // Замените на ваш пароль
                denis.setCreatedBy("system"); // Устанавливаем createdBy
                denis.setRoles(List.of(userRole));
                denis = userRepository.save(denis);
            }

            if (!denis.getRoles().contains(adminRole)) {
                List<Role> roles = new ArrayList<>(denis.getRoles());
                roles.add(adminRole);
                denis.setRoles(roles);
                userRepository.save(denis);
            }
        };
    }
}
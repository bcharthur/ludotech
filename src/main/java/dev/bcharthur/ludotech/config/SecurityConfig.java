package dev.bcharthur.ludotech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Active la protection CSRF par défaut :
                .csrf(Customizer.withDefaults())
                // Configuration d’accès
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index", "/register", "/login", "/check-email",
                                "/check-password", "/css/**", "/js/**")
                        .permitAll()
                        .requestMatchers("/api/jeux", "/api/genres", "/api/jeu/{id}", "/api/jeu/**")
                        .permitAll()
                        .requestMatchers("/api/bgg/**")
                        .permitAll()
                        .requestMatchers("/api/exemplaires/**")
                        .permitAll()
                        // Nouvelle règle pour la disponibilité
                        .requestMatchers("/admin/exemplaire/disponibilite/**")
                        .permitAll()
                        // Règles d'accès admin
                        .requestMatchers("/admin/**")
                        .hasAnyRole("admin", "employe")
                        .anyRequest().authenticated()
                )
                // Configuration du formLogin
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                // Configuration du logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

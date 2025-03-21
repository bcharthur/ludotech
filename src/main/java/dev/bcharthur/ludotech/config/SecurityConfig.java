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
                        // Autoriser l'accès public aux URL suivantes
                        .requestMatchers("/", "/index", "/register", "/login", "/check-email", "/check-password", "/css/**", "/js/**")
                        .permitAll()
                        // Autoriser l'accès public aux APIs de récupération des jeux et genres
                        .requestMatchers("/api/jeux", "/api/genres", "/api/jeu/{id}", "/api/jeu/**")
                        .permitAll()
                        // Autoriser l'accès public aux API d'importation depuis BoardGameGeek
                        .requestMatchers("/api/bgg/**")
                        .permitAll()
                        // Autoriser l'accès public aux endpoints d'exemplaires (notamment notre nouvel endpoint)
                        .requestMatchers("/api/exemplaires/**")
                        .permitAll()
                        // Autoriser seulement les administrateurs à accéder aux endpoints commençant par /admin
                        .requestMatchers("/admin/**")
                        .hasAnyRole("admin", "employe")
                        // Toute autre URL nécessite d'être simplement authentifié
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

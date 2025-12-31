package com.lil.insta.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.lil.insta.service.user.UserInfoService;
import com.lil.insta.util.JwtAuthFilter;

import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Configuration
@EnableWebSecurity
public class BeanConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow Angular dev server
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Allow common HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow headers like Content-Type, Authorization
        configuration.setAllowedHeaders(List.of("Content-Type","Authorization"));

        // Allow cookies/credentials if needed
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    /*
     * Main security configuration
     * Defines endpoint access rules and JWT filter setup
     */
    @Bean
    public SecurityFilterChain securityFilterChain(UserInfoService userInfoService, JwtAuthFilter jwtAuthFilter, HttpSecurity http)
            throws Exception {
        http
                .cors(Customizer.withDefaults())
                // Disable CSRF (not needed for stateless JWT)
                .csrf(csrf -> csrf.disable())

                // Configure endpoint authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/register", "/auth/login", "/auth/health", "/actuator/**").permitAll()

                        // Role-based endpoints
                        // .requestMatchers("/auth/user/**").hasAuthority("ROLE_USER")
                        // .requestMatchers("/auth/admin/**").hasAuthority("ROLE_ADMIN")

                        // All other endpoints require JWT authentication
                        .anyRequest().authenticated())

                // Stateless session (required for JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Set custom authentication provider
                .authenticationProvider(authenticationProvider(userInfoService))

                // Add JWT filter before Spring Security's default filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * Password encoder bean (uses BCrypt hashing)
     * Critical for secure password storage
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Authentication provider configuration
     * Links UserDetailsService and PasswordEncoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserInfoService userInfoService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userInfoService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /*
     * Authentication manager bean
     * Required for programmatic authentication (e.g., in /generateToken)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecretsManagerClient secretsManagerClient(){
        SecretsManagerClient secrets = SecretsManagerClient.builder().build();
        return secrets;
    }
}

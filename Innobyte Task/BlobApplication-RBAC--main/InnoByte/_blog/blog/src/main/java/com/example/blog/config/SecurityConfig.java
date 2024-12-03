package com.example.blog.config;

import com.example.blog.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity // Enables Spring Security's web security support
@EnableMethodSecurity // Enables method-level security annotations (e.g., @PreAuthorize)
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter; // Custom JWT authentication filter for processing JWT tokens

    @Bean
    // Bean that provides custom user details for authentication
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    // Configures the security filter chain for handling HTTP requests
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() // Disables CSRF protection (should be reconsidered for certain use cases)
                .authorizeHttpRequests()
                .requestMatchers("/BlogApp/new", "/BlogApp/authenticate").permitAll() // Publicly accessible endpoints
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/products/**","/posts/**","/comments/**").authenticated() // Requires authentication for /products/** endpoints
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configures session management to stateless (for JWT)
                .and()
                .authenticationProvider(authenticationProvider()) // Uses the custom AuthenticationProvider
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) // Adds the JWT filter before the default filter
                .build();
    }

    @Bean
    // Bean that provides the password encoder (BCrypt is used here)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Bean that sets up the authentication provider for authenticating user credentials
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService()); // Sets the custom user details service
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Sets the password encoder
        return authenticationProvider;
    }

    @Bean
    // Bean that provides the authentication manager used by Spring Security
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
        }
}


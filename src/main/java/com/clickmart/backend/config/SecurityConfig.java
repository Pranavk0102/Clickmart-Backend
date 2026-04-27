package com.clickmart.backend.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Value("${app.cors.allowed-origins:http://localhost:5173}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/login", "/auth/register", "/auth/refresh-token", "/auth/logout", "/auth/forgot-password", "/auth/reset-password").permitAll()
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/coupons").permitAll()
                .requestMatchers(HttpMethod.POST, "/coupons/validate").permitAll()
                .requestMatchers(HttpMethod.GET, "/delivery/options").permitAll()
                .requestMatchers(HttpMethod.GET, "/delivery/all").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/coupons/all").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/coupons").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/coupons/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/coupons/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/delivery/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/delivery/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/delivery/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/support/tickets").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/support/tickets/*/respond").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/orders/ORD-*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/orders/*/status").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        String[] origins = allowedOrigins.split(",");
        for (int i = 0; i < origins.length; i++) {
            origins[i] = origins[i].trim();
        }
        configuration.setAllowedOriginPatterns(Arrays.asList(origins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

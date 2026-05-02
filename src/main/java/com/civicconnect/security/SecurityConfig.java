package com.civicconnect.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())

            // ✅ IMPORTANT: no session (REST API mode)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ✅ OPEN EVERYTHING FOR NOW (fixes 403)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/test",
                    "/api/test",
                    "/api/auth/**",
                    "/api/users/**",
                    "/api/issues/**"
                ).permitAll()

                // fallback (also open everything for now)
                .anyRequest().permitAll()
            )

            // ❌ disable default login
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        // ❌ TEMP: disable JWT filter for testing
        // REMOVE THIS LINE UNTIL YOU WANT SECURITY
        // http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

package tip.java.barraca_lenia.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()).addFilterBefore(
                new SeguridadConfig(),
                UsernamePasswordAuthenticationFilter.class
        ) .authorizeHttpRequests(auth -> auth

                        //temporal para pruebas
                        .requestMatchers("/api/v1/**").permitAll()

                        // SOLO LOGIN PUBLICO
                        .requestMatchers("/api/v1/seguridad/login").permitAll()

                        .anyRequest().authenticated()
                );


        return http.build();
    }
}
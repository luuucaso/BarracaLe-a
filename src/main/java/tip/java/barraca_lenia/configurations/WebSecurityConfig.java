package tip.java.barraca_lenia.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/**").permitAll()
                        .requestMatchers("/crearUsuario/**").permitAll()
                        .requestMatchers("/eliminarUsuario/**").permitAll()
                        .requestMatchers("/actualizarUsuario/**").permitAll()
                        .requestMatchers("/listarUsuarios/**").permitAll()
                        .requestMatchers("/crearRol/**").permitAll()
                        .requestMatchers("/borrarRol/**").permitAll()
                        .requestMatchers("/actualizarRol/**").permitAll()
                        .requestMatchers("/listarRoles/**").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
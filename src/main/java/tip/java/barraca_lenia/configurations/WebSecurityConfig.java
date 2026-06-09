package tip.java.barraca_lenia.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final SeguridadConfig seguridadConfig;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(seguridadConfig, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/seguridad/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuario/registrarUsuario").permitAll()
                        // TEMPORAL: todos los GET públicos (catálogo, presentaciones, productos, etc.)
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/direcciones/**").permitAll()
                        .requestMatchers("/cliente-anonimo/**").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/pedidos/crearPedido")
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/direcciones/**"
                        ).authenticated()
                        .requestMatchers("/api/v1/pedidos/**").hasAnyAuthority("ADMIN", "Administrador")
                        .requestMatchers("/api/v1/estados/**").hasAnyAuthority("ADMIN", "Administrador")
                        .requestMatchers("/api/v1/producto/**").hasAnyAuthority("ADMIN", "Administrador")
                        .requestMatchers("/api/v1/presentacion/**").hasAnyAuthority("ADMIN", "Administrador")
                        .requestMatchers("/api/v1/usuario/**").hasAnyAuthority("ADMIN", "Administrador")
                        .requestMatchers("/api/v1/rol/**").hasAnyAuthority("ADMIN", "Administrador")
                        .requestMatchers("/api/v1/imagenProducto/**").hasAnyAuthority("ADMIN", "Administrador")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN))
                );

        return http.build();
    }
}

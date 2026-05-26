package tip.java.barraca_lenia.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.services.SeguridadService;
import tip.java.barraca_lenia.dto.LoginResponseDTO;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/seguridad")
@AllArgsConstructor
public class SeguridadController {

    private final SeguridadService seguridadService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuario =
                seguridadService.login(usuarioDTO.getTelefono(), usuarioDTO.getPassword());

        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Teléfono o contraseña incorrectos"));
        }

        return ResponseEntity.ok(seguridadService.crearRespuestaLogin(usuario.get()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String telefono = auth.getName();
        return seguridadService.buscarPorTelefono(telefono)
                .map(usuario -> {
                    LoginResponseDTO respuesta = seguridadService.crearRespuestaLogin(usuario);
                    respuesta.setToken(null);
                    return ResponseEntity.ok(respuesta);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}

package tip.java.barraca_lenia.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.services.SeguridadService;
import tip.java.barraca_lenia.dto.UsuarioDTO;

@RestController
@RequestMapping("/api/v1/seguridad")
@AllArgsConstructor


public class SeguridadController {

    private final SeguridadService seguridadService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {

        Usuario usuarioBd = seguridadService
                .login(usuarioDTO.getTelefono(), usuarioDTO.getPassword())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = seguridadService.generarToken(usuarioBd);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}

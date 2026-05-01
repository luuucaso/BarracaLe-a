package tip.java.barraca_lenia.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.services.UsuarioService;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.List;

@RestController

@AllArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;

    //crear
    @PostMapping("/crearUsuario")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO creado = usuarioService.crearUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //borrar
    @DeleteMapping("/eliminarUsuario/{telefono}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String telefono) {
        usuarioService.borrarUsuario(telefono);

        return ResponseEntity.noContent().build();
    }

    //actualizar
    @PutMapping("/actualizarUsuario/{telefono}")
    public ResponseEntity<UsuarioDTO> modificarUsuario(@PathVariable String telefono, @RequestBody UsuarioDTO usuarioDTO) {

        UsuarioDTO actualizado = usuarioService.actualizarUsuario(telefono, usuarioDTO);

        return ResponseEntity.ok(actualizado);
    }

    //listar
    @GetMapping("/listarUsuarios")
    public List<UsuarioDTO> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }






}

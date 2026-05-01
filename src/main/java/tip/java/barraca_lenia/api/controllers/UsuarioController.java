package tip.java.barraca_lenia.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.services.UsuarioService;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barraca")
@AllArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;

    //crear
    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }

    //borrar
    @DeleteMapping("/usuarios/{telefono}")
    public Boolean eliminarUsuario(@PathVariable String telefono) {
        return usuarioService.borrarUsuario(telefono);
    }

    //actualzar
    @PutMapping("/usuarios/{telefono}")
    public Usuario modificarUsuario(@PathVariable String telefono, @RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO.setTelefono(telefono);
        return usuarioService.actualizarUsuario(usuarioDTO);
    }

    //listar
    @GetMapping("/usuarios")
    public List<UsuarioDTO> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }






}

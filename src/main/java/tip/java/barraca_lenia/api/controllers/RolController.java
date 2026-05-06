package tip.java.barraca_lenia.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.services.RolService;
import tip.java.barraca_lenia.dto.RolDTO;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rol")
public class RolController {

    private final RolService rolService;

    //crear rol
    @PostMapping("/crearRol")
    public ResponseEntity<RolDTO> crearRol(@RequestBody RolDTO rolDTO) {

        RolDTO creado = rolService.crearRol(rolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //borrar rol
    @DeleteMapping("/borrarRol/{id}")
    public ResponseEntity<Void> borrarRol(@PathVariable Long id) {
        rolService.borrarRol(id);

        return ResponseEntity.noContent().build();
    }

    //actualizar rol
    @PutMapping("/actualizarRol/{id}")
    public ResponseEntity<RolDTO> actualizarRol(@PathVariable Long id, @RequestBody RolDTO rolDTO) {

        RolDTO actualizado = rolService.actualizarRol(id, rolDTO);

        return ResponseEntity.ok(actualizado);
    }

    //listar
    @GetMapping("/listarRoles")
    public List<RolDTO> listarRoles(){
        return rolService.listarRoles();
    }

}

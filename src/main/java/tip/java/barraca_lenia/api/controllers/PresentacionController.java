package tip.java.barraca_lenia.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.services.PresentacionService;
import tip.java.barraca_lenia.dto.PresentacionDTO;
import tip.java.barraca_lenia.dto.ProductoDTO;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.List;


@RestController
@RequestMapping("/api/v1/presentacion")
@AllArgsConstructor

public class PresentacionController {


    private final PresentacionService presentacionService;

    @PostMapping("/crearPresentacion")
    public ResponseEntity<PresentacionDTO> crearUsuario(@RequestBody PresentacionDTO presentacionDTO) {

        PresentacionDTO creado = presentacionService.crearPresentacion(presentacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/listarPresentacion")
    public List<PresentacionDTO> listarPresentacion(){
        return presentacionService.listarPresentacion();
    }

    @DeleteMapping("/eliminarPresentacion/{id}")
    public ResponseEntity<Void> eliminarPresentacion(@PathVariable Long id) {
        presentacionService.borrarPresentacion(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/actualizarPresentacion/{id}")
    public ResponseEntity<PresentacionDTO> modificarPresentacion(@PathVariable Long id, @RequestBody PresentacionDTO presentacionDTO) {

        PresentacionDTO actualizado = presentacionService.actualizarPresentacion(presentacionDTO, id);

        return ResponseEntity.ok(actualizado);
    }

}

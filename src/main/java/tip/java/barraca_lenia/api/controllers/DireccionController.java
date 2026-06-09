package tip.java.barraca_lenia.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.entities.Direccion;
import tip.java.barraca_lenia.biz.dao.services.DireccionService;
import tip.java.barraca_lenia.dto.DireccionDTO;
import tip.java.barraca_lenia.dto.PedidoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/direcciones")
@AllArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping("/usuario/{id}")
    public List<Direccion> obtenerPorUsuario(
            @PathVariable Long id
    ) {
        return direccionService.obtenerPorUsuario(id);
    }

    @PostMapping("/agregarDireccion")
    public ResponseEntity<DireccionDTO> agregarDireccion(
            @RequestBody DireccionDTO dto
    ) {
        return ResponseEntity.ok(direccionService.agregarDireccion(dto));
    }
}

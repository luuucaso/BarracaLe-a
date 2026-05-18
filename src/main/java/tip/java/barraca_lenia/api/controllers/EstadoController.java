package tip.java.barraca_lenia.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.services.EstadoService;
import tip.java.barraca_lenia.dto.EstadoDTO;
import tip.java.barraca_lenia.dto.PedidoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estados")
@AllArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping("/listarEstados")
    public ResponseEntity<List<EstadoDTO>> listarEstados() {
        return ResponseEntity.ok(estadoService.listarEstados());
    }

}

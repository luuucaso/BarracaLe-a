package tip.java.barraca_lenia.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.services.PedidoService;
import tip.java.barraca_lenia.dto.PedidoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/crearPedido")
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO dto) {
        return ResponseEntity.ok(pedidoService.crearPedido(dto));
    }

    @GetMapping("/listarPedidos")
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @PutMapping("/actualizarPedido/{idPedido}")
    public ResponseEntity<PedidoDTO> actualizarPedido(
            @PathVariable Long idPedido,
            @RequestBody PedidoDTO dto
    ) {
        return ResponseEntity.ok(pedidoService.actualizarPedido(idPedido, dto));
    }

    @PutMapping("/actualizarEstadoPedido/{idPedido}")
    public ResponseEntity<PedidoDTO> actualizarEstadoPedido(
            @PathVariable Long idPedido,
            @RequestBody PedidoDTO dto
    ) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoPedido(idPedido, dto));
    }

    @DeleteMapping("borrarPedido/{idPedido}")
    public ResponseEntity<Void> borrarPedido(@PathVariable Long idPedido) {
        pedidoService.borrarPedido(idPedido);
        return ResponseEntity.noContent().build();
    }

}

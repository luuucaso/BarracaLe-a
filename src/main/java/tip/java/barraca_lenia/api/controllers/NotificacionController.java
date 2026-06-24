package tip.java.barraca_lenia.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.services.NotificacionService;
import tip.java.barraca_lenia.dto.NotificacionDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificacion")
@AllArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;


    @PostMapping("/crearNotificacion")
    public ResponseEntity<NotificacionDTO> crearNotificacion(@RequestBody NotificacionDTO notificacionDTO) {

        NotificacionDTO creada = notificacionService.crearNotificacion(notificacionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }


    @DeleteMapping("/eliminarNotificacion/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {

        notificacionService.borrarNotificacion(id);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/marcarComoLeida/{id}")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Long id) {

        NotificacionDTO actualizada = notificacionService.marcarComoLeida(id);

        return ResponseEntity.ok(actualizada);
    }


    @PutMapping("/marcarTodasComoLeidasPorUsuario/{idUsuario}")
    public ResponseEntity<Void> marcarTodasComoLeidasPorUsuario(@PathVariable Long idUsuario) {

        notificacionService.marcarTodasComoLeidasPorUsuario(idUsuario);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/listarNotificaciones")
    public List<NotificacionDTO> listarNotificaciones() {

        return notificacionService.listarNotificaciones();
    }


    @GetMapping("/listarNotificacionesPorUsuario/{idUsuario}")
    public List<NotificacionDTO> listarNotificacionesPorUsuario(@PathVariable Long idUsuario) {

        return notificacionService.listarNotificacionesPorUsuario(idUsuario);
    }


    @GetMapping("/listarNotificacionesPorPedido/{idPedido}")
    public List<NotificacionDTO> listarNotificacionesPorPedido(@PathVariable Integer idPedido) {

        return notificacionService.listarNotificacionesPorPedido(idPedido);
    }
}
package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Notificacion;
import tip.java.barraca_lenia.biz.dao.entities.Pedido;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.repositories.NotificacionRepository;
import tip.java.barraca_lenia.biz.dao.repositories.PedidoRepository;
import tip.java.barraca_lenia.biz.dao.repositories.UsuarioRepository;
import tip.java.barraca_lenia.dto.NotificacionDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacionDTO crearNotificacion(NotificacionDTO dto) {

        Pedido pedido = pedidoRepository.findById(Long.valueOf(dto.getIdPedido()))
                .orElseThrow(() -> new RuntimeException("No existe el pedido con el id: " + dto.getIdPedido()));

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("No existe el usuario con el id: " + dto.getIdUsuario()));

        Notificacion notificacion = new Notificacion();

        notificacion.setMensaje(dto.getMensaje());
        notificacion.setFechaHora(LocalDateTime.now());
        notificacion.setLeido(false);
        notificacion.setPedido(pedido);
        notificacion.setUsuario(usuario);

        Notificacion guardada = notificacionRepository.save(notificacion);

        return mapeo(guardada);
    }

    public void borrarNotificacion(Long id) {

        Optional<Notificacion> existente = notificacionRepository.findById(id);

        if (existente.isEmpty()) {
            throw new RuntimeException("No existe la notificación con el id: " + id);
        }

        notificacionRepository.delete(existente.get());
    }

    public NotificacionDTO marcarComoLeida(Long id) {

        Optional<Notificacion> existente = notificacionRepository.findById(id);

        if (existente.isEmpty()) {
            throw new RuntimeException("No existe la notificación con el id: " + id);
        }

        Notificacion notificacion = existente.get();

        notificacion.setLeido(true);

        Notificacion actualizada = notificacionRepository.save(notificacion);

        return mapeo(actualizada);
    }

    public void marcarTodasComoLeidasPorUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con el id: " + idUsuario));

        List<Notificacion> notificaciones =
                notificacionRepository.findByUsuarioIdAndLeidoFalse(usuario.getId());

        notificaciones.forEach(notificacion -> {
            notificacion.setLeido(true);
        });

        notificacionRepository.saveAll(notificaciones);
    }

    public List<NotificacionDTO> listarNotificaciones() {
        return notificacionRepository.findAll()
                .stream()
                .map(this::mapeo)
                .toList();
    }

    public List<NotificacionDTO> listarNotificacionesPorUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con el id: " + idUsuario));

        return notificacionRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(this::mapeo)
                .toList();
    }

    public List<NotificacionDTO> listarNotificacionesPorPedido(Integer idPedido) {

        Pedido pedido = pedidoRepository.findById(Long.valueOf(idPedido))
                .orElseThrow(() -> new RuntimeException("No existe el pedido con el id: " + idPedido));

        return notificacionRepository.findByPedidoId(pedido.getId())
                .stream()
                .map(this::mapeo)
                .toList();
    }

    private NotificacionDTO mapeo(Notificacion notificacion) {

        NotificacionDTO dto = new NotificacionDTO();

        dto.setId(notificacion.getId());
        dto.setMensaje(notificacion.getMensaje());
        dto.setFechaHora(notificacion.getFechaHora());
        dto.setLeido(notificacion.isLeido());

        if (notificacion.getPedido() != null) {
            dto.setIdPedido(notificacion.getPedido().getId());
        }

        if (notificacion.getUsuario() != null) {
            dto.setIdUsuario(notificacion.getUsuario().getId());
        }

        return dto;
    }
}
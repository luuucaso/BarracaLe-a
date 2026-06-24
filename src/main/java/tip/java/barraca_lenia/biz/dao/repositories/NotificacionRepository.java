package tip.java.barraca_lenia.biz.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tip.java.barraca_lenia.biz.dao.entities.Notificacion;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByUsuarioId(Long idUsuario);

    List<Notificacion> findByPedidoId(Integer idPedido);

    List<Notificacion> findByUsuarioIdAndLeidoFalse(Long idUsuario);
}
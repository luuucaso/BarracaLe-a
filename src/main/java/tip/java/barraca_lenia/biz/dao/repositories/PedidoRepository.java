package tip.java.barraca_lenia.biz.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tip.java.barraca_lenia.biz.dao.entities.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

    List<Pedido> findByUsuarioId(Long idUsuario);
}

package tip.java.barraca_lenia.biz.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tip.java.barraca_lenia.biz.dao.entities.Presentacion;
import tip.java.barraca_lenia.biz.dao.entities.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresentacionRepository  extends JpaRepository<Presentacion,Long> {

    Optional<Presentacion> findById(Long id);
    Optional<Presentacion> findByDescripcionAndProductoId(String descripcion, Long productoId);
    @Query("SELECT p FROM Presentacion p JOIN FETCH p.producto")
    List<Presentacion> findAllConProducto();
    List<Presentacion> findTop3ByOrderByIdDesc();
}

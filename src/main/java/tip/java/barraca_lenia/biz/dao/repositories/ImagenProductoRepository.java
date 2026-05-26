package tip.java.barraca_lenia.biz.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tip.java.barraca_lenia.biz.dao.entities.ImagenProducto;

import java.util.List;

@Repository
public interface ImagenProductoRepository extends JpaRepository<ImagenProducto,Long> {


    List<ImagenProducto> findByProductoId(Long idProducto);

    @Query("SELECT i FROM ImagenProducto i JOIN FETCH i.producto JOIN FETCH i.presentacion")
    List<ImagenProducto> findAllConProductoYPresentacion();

}

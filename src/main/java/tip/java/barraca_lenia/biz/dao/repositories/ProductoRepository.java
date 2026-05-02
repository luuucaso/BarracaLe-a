package tip.java.barraca_lenia.biz.dao.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tip.java.barraca_lenia.biz.dao.entities.Producto;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    @Override
    public Optional<Producto> findById(Long id);
    public Optional<Producto> findByNombre(String nombre);
}

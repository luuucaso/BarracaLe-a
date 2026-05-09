package tip.java.barraca_lenia.biz.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tip.java.barraca_lenia.biz.dao.entities.Rol;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    public Optional<Rol> findByNombre(String nombre);
    public Optional<Rol> findById(Long id);

}

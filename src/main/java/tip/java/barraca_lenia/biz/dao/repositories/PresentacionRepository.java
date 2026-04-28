package tip.java.barraca_lenia.biz.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tip.java.barraca_lenia.biz.dao.entities.Presentacion;

@Repository
public interface PresentacionRepository  extends JpaRepository<Presentacion,Long> {
}

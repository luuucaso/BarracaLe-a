package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Direccion;
import tip.java.barraca_lenia.biz.dao.repositories.DireccionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DireccionService {
    private final DireccionRepository direccionRepository;

    public List<Direccion> obtenerPorUsuario(Long idUsuario) {

        return direccionRepository
                .findByUsuarioId(idUsuario);
    }
}

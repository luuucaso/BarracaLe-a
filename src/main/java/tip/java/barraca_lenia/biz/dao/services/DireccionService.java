package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Direccion;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.repositories.DireccionRepository;
import tip.java.barraca_lenia.biz.dao.repositories.PresentacionRepository;
import tip.java.barraca_lenia.biz.dao.repositories.UsuarioRepository;
import tip.java.barraca_lenia.dto.DireccionDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class DireccionService {
    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Direccion> obtenerPorUsuario(Long idUsuario) {

        return direccionRepository
                .findByUsuarioId(idUsuario);
    }

    public DireccionDTO agregarDireccion(DireccionDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Direccion direccion = new Direccion();
        direccion.setCalle(dto.getCalle());
        direccion.setNumeroCasa(dto.getNumeroCasa());
        direccion.setReferencia(dto.getReferencia());
        direccion.setAlias(dto.getAlias());
        direccion.setUsuario(usuario);

        Direccion guardada = direccionRepository.save(direccion);

        DireccionDTO respuesta = new DireccionDTO();
        respuesta.setIdUsuario(usuario.getId());
        respuesta.setCalle(guardada.getCalle());
        respuesta.setNumeroCasa(guardada.getNumeroCasa());
        respuesta.setReferencia(guardada.getReferencia());
        respuesta.setAlias(guardada.getAlias());

        return respuesta;
    }

}

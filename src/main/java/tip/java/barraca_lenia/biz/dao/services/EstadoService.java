package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Estado;
import tip.java.barraca_lenia.biz.dao.entities.Pedido;
import tip.java.barraca_lenia.biz.dao.repositories.EstadoRepository;
import tip.java.barraca_lenia.dto.EstadoDTO;
import tip.java.barraca_lenia.dto.PedidoDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    //Listar pedidos
    public List<EstadoDTO> listarEstados() {
        return estadoRepository.findAll()
                .stream()
                .map(this::mapeo)
                .toList();
    }

    private EstadoDTO mapeo(Estado estado) {
        EstadoDTO dto = new EstadoDTO();

        dto.setId(estado.getId());
        dto.setEstado(estado.getEstado());

        return dto;
    }

}

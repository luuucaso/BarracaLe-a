package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.ClienteAnonimo;
import tip.java.barraca_lenia.biz.dao.repositories.ClienteAnonimoRepository;
import tip.java.barraca_lenia.dto.ClienteAnonimoDTO;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClienteAnonimoService {

    private final ClienteAnonimoRepository clienteAnonimoRepository;

    public ClienteAnonimo buscarOrCrearClienteAnonimo(String token) {

        if (token != null) {
            Optional<ClienteAnonimo> clienteExistente = clienteAnonimoRepository.findByToken(token);

            if (clienteExistente.isPresent()) {
                return clienteExistente.get();
            }
        }

        ClienteAnonimo nuevoCliente = new ClienteAnonimo();

        String nuevoToken = UUID.randomUUID().toString();
        nuevoCliente.setToken(nuevoToken);

        return clienteAnonimoRepository.save(nuevoCliente);
    }

    public ClienteAnonimo actualizarDatos(String token, ClienteAnonimoDTO datos) {
        ClienteAnonimo cliente = buscarOrCrearClienteAnonimo(token);

        cliente.setNombre(datos.getNombre());
        cliente.setTelefono(datos.getTelefono());
        cliente.setCalle(datos.getCalle());
        cliente.setNumeroCasa(datos.getNumeroCasa());
        cliente.setReferencia(datos.getReferencia());

        return clienteAnonimoRepository.save(cliente);
    }

    public ClienteAnonimoDTO mapeo(ClienteAnonimo cliente) {
        ClienteAnonimoDTO dto = new ClienteAnonimoDTO();

        dto.setId(cliente.getId());
        dto.setToken(cliente.getToken());
        dto.setNombre(cliente.getNombre());
        dto.setTelefono(cliente.getTelefono());
        dto.setCalle(cliente.getCalle());
        dto.setNumeroCasa(cliente.getNumeroCasa());
        dto.setReferencia(cliente.getReferencia());

        return dto;
    }
}

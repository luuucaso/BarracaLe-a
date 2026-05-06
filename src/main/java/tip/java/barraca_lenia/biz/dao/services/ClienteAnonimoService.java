package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.ClienteAnonimo;
import tip.java.barraca_lenia.biz.dao.repositories.ClienteAnonimoRepository;

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
}

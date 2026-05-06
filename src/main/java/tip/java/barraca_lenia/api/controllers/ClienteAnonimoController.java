package tip.java.barraca_lenia.api.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.entities.ClienteAnonimo;
import tip.java.barraca_lenia.biz.dao.services.ClienteAnonimoService;
import tip.java.barraca_lenia.dto.ClienteAnonimoDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/cliente-anonimo")
public class ClienteAnonimoController {

    private final ClienteAnonimoService clienteAnonimoService;

    @GetMapping
    public ResponseEntity<ClienteAnonimoDTO> obtenerClienteAnonimo(
            @CookieValue(value = "cliente_token", required = false) String token,
            HttpServletResponse response
    ) {

        ClienteAnonimo cliente = clienteAnonimoService.buscarOrCrearClienteAnonimo(token);

        if (token == null) {
            Cookie cookie = new Cookie("cliente_token", cliente.getToken());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 24 * 30);

            response.addCookie(cookie);
        }

        ClienteAnonimoDTO dto = mapToDTO(cliente);

        return ResponseEntity.ok(dto);
    }

    private ClienteAnonimoDTO mapToDTO(ClienteAnonimo cliente) {
        ClienteAnonimoDTO dto = new ClienteAnonimoDTO();

        dto.setNombre(cliente.getNombre());
        dto.setTelefono(cliente.getTelefono());
        dto.setCalle(cliente.getCalle());
        dto.setNumeroCasa(cliente.getNumeroCasa());
        dto.setReferencia(cliente.getReferencia());

        return dto;
    }
}

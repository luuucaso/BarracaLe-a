package tip.java.barraca_lenia.api.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

        System.out.println("TOKEN RECIBIDO: " + token);

        ClienteAnonimo cliente = clienteAnonimoService.buscarOrCrearClienteAnonimo(token);

        if (token == null) {
            ResponseCookie cookie = ResponseCookie.from("cliente_token", cliente.getToken())
                    .path("/")
                    .httpOnly(true)
                    .maxAge(60 * 60 * 24 * 30)
                    .sameSite("Lax")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }

        ClienteAnonimoDTO dto = clienteAnonimoService.mapeo(cliente);

        return ResponseEntity.ok(dto);
    }


}

package tip.java.barraca_lenia.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tip.java.barraca_lenia.biz.dao.entities.Direccion;
import tip.java.barraca_lenia.biz.dao.services.DireccionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/direcciones")
@AllArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping("/usuario/{id}")
    public List<Direccion> obtenerPorUsuario(
            @PathVariable Long id
    ) {
        return direccionService.obtenerPorUsuario(id);
    }
}

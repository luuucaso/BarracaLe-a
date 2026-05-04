package tip.java.barraca_lenia.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tip.java.barraca_lenia.biz.dao.services.PresentacionService;
import tip.java.barraca_lenia.dto.PresentacionDTO;


@RestController
@RequestMapping("/api/v1/presentacion")
@AllArgsConstructor

public class PresentacionController {


    private final PresentacionService presentacionService;

    @PostMapping("/crearPresentacion")
    public ResponseEntity<PresentacionDTO> crearUsuario(@RequestBody PresentacionDTO presentacionDTO) {

        PresentacionDTO creado = presentacionService.crearPresentacion(presentacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

}

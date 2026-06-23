package tip.java.barraca_lenia.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.services.ImagenProductoService;
import tip.java.barraca_lenia.biz.dao.services.PresentacionService;
import tip.java.barraca_lenia.dto.ImagenProductoDTO;
import tip.java.barraca_lenia.dto.PresentacionDTO;

import java.util.List;


@RestController
@RequestMapping("/api/v1/imagenProducto")
@AllArgsConstructor

public class ImagenProductoController {


    private final ImagenProductoService imagenProductoService;

    @PostMapping("/crearImagen")
    public ResponseEntity<ImagenProductoDTO> crearImagen(@RequestBody ImagenProductoDTO imagenProductoDTO) {

        ImagenProductoDTO creado = imagenProductoService.crearImagenProducto(imagenProductoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/listarImagenes")
    public List<ImagenProductoDTO> listarImagenes() {

        return imagenProductoService.listarImagenes();
    }

    @GetMapping("/presentacion/{idPresentacion}")
    public ResponseEntity<ImagenProductoDTO> obtenerImagenPorPresentacion(
            @PathVariable Long idPresentacion
    ) {
        ImagenProductoDTO imagen =
                imagenProductoService.obtenerImagenPorPresentacion(idPresentacion);

        return ResponseEntity.ok(imagen);
    }



}

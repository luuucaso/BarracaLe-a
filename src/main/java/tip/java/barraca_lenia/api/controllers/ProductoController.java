package tip.java.barraca_lenia.api.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tip.java.barraca_lenia.biz.dao.services.ProductoService;
import tip.java.barraca_lenia.dto.ProductoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/producto")
@AllArgsConstructor

public class ProductoController {

    private final ProductoService productoService;

    //crear
    @PostMapping("/crearProducto")
    public ResponseEntity<ProductoDTO> crearUsuario(@RequestBody ProductoDTO productoDTO) {

        ProductoDTO creado = productoService.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //borrar
    @DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);

        return ResponseEntity.noContent().build();
    }

    //actualizar
    @PutMapping("/actualizarProducto/{id}")
    public ResponseEntity<ProductoDTO> modificarUsuario(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {

        ProductoDTO actualizado = productoService.actualizarProducto(productoDTO, id);

        return ResponseEntity.ok(actualizado);
    }

    //listar
    @GetMapping("/listarProducto")
    public List<ProductoDTO> listarProductos(){
        return productoService.listarProductos();
    }






}

package tip.java.barraca_lenia.biz.dao.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Presentacion;
import tip.java.barraca_lenia.biz.dao.entities.Producto;
import tip.java.barraca_lenia.biz.dao.repositories.PresentacionRepository;
import tip.java.barraca_lenia.biz.dao.repositories.ProductoRepository;
import tip.java.barraca_lenia.dto.PresentacionDTO;
import tip.java.barraca_lenia.dto.ProductoDTO;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PresentacionService {
    private final PresentacionRepository presentacionRepository;
    private final ProductoRepository productoRepository;

    public PresentacionDTO crearPresentacion(PresentacionDTO presentacionDTO) {

        Optional<Presentacion> existente = presentacionRepository.findByDescripcionAndProductoId(presentacionDTO.getDescripcion(), presentacionDTO.getIdProducto());

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe esa presentacion");
        }

        Presentacion presentacion= new Presentacion();

       presentacion.setDescripcion(presentacionDTO.getDescripcion());
       presentacion.setCantidad(presentacionDTO.getCantidad());
       presentacion.setPrecio(presentacionDTO.getPrecio());
       presentacion.setUnidadMedida(presentacionDTO.getUnidadMedida());
       Producto producto = productoRepository.findById(presentacionDTO.getIdProducto()).
               orElseThrow(() -> new RuntimeException("Producto no encontrado"));

       presentacion.setProducto(producto);

        Presentacion guardado = presentacionRepository.save(presentacion);

        return mapeo(guardado);


    }
/*
    public void borrarProducto(Long id) {

        Optional<Producto> existente = productoRepository.findById(id);

        if (existente.isEmpty()) {
            throw new RuntimeException("No existe el prducto con el id: " + id);
        }
        productoRepository.delete(existente.get());

    }

    public ProductoDTO actualizarProducto(ProductoDTO productoDTO, Long id) {
        Optional<Producto> existente = productoRepository.findById(id);

        if (existente.isEmpty()) {
            throw new RuntimeException("No existe el prducto con el id: " + productoDTO.getId());
        }

        Producto producto = existente.get();

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setTipoUso(productoDTO.getTipoUso());
        producto.setActivo(productoDTO.getActivo());

        Producto actualizado = productoRepository.save(producto);
        return mapeo(actualizado);

    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream().map(producto->{
            ProductoDTO p = new ProductoDTO();
            p.setId(producto.getId());
            p.setNombre(producto.getNombre());
            p.setDescripcion(producto.getDescripcion());
            p.setTipoUso(producto.getTipoUso());
            p.setActivo(producto.getActivo());
            return p;
        }).toList();

    }
*/
    private PresentacionDTO mapeo(Presentacion presentacion) {
        PresentacionDTO presentacionDTO = new PresentacionDTO();
        presentacionDTO.setId(presentacion.getId());
        presentacionDTO.setCantidad(presentacion.getCantidad());
        presentacionDTO.setDescripcion(presentacion.getDescripcion());
        presentacionDTO.setPrecio(presentacion.getPrecio());
        presentacionDTO.setUnidadMedida(presentacion.getUnidadMedida());
        presentacionDTO.setIdProducto(presentacion.getProducto().getId());
        return presentacionDTO;

    }

}

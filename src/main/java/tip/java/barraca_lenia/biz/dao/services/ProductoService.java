package tip.java.barraca_lenia.biz.dao.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Producto;
import tip.java.barraca_lenia.biz.dao.repositories.ProductoRepository;
import tip.java.barraca_lenia.dto.ProductoDTO;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {

        Optional<Producto> existente = productoRepository.findByNombre(productoDTO.getNombre());

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe el prducto con el id: " + productoDTO.getId());
        }

        Producto producto= new Producto();

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setTipoUso(productoDTO.getTipoUso());
        producto.setActivo(productoDTO.getActivo());

        Producto guardado = productoRepository.save(producto);

        return mapeo(guardado);


    }

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

    private ProductoDTO mapeo(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setActivo(producto.getActivo());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setTipoUso(producto.getTipoUso());
        return productoDTO;

    }
}

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

    public void borrarPresentacion(Long id) {

        Optional<Presentacion> existente = presentacionRepository.findById(id);

        if (existente.isEmpty()) {
            throw new RuntimeException("No existe la presentacion con el id: " + id);
        }
        presentacionRepository.delete(existente.get());

    }

    public PresentacionDTO actualizarPresentacion(PresentacionDTO presentacionDTO, Long id) {
        Optional<Presentacion> existente = presentacionRepository.findById(id);

        if (existente.isEmpty()) {
            throw new RuntimeException("No existe la presentacion con el id: " + id);
        }

        Presentacion presentacion = existente.get();

        presentacion.setDescripcion(presentacionDTO.getDescripcion());
        presentacion.setCantidad(presentacionDTO.getCantidad());
        presentacion.setPrecio(presentacionDTO.getPrecio());
        presentacion.setUnidadMedida(presentacionDTO.getUnidadMedida());
        Producto producto = productoRepository.findById(presentacionDTO.getIdProducto()).
                orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        presentacion.setProducto(producto);


        Presentacion actualizado = presentacionRepository.save(presentacion);
        return mapeo(actualizado);

    }

    public List<PresentacionDTO> listarPresentacion() {
        return presentacionRepository.findAll().stream().map(presentacion->{
            PresentacionDTO p = new PresentacionDTO();
            p.setId(presentacion.getId());
            p.setDescripcion(presentacion.getDescripcion());
            p.setCantidad(presentacion.getCantidad());
            p.setPrecio(presentacion.getPrecio());
            p.setUnidadMedida(presentacion.getUnidadMedida());
            p.setIdProducto(presentacion.getProducto().getId());
            return p;
        }).toList();

    }

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

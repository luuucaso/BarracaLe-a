package tip.java.barraca_lenia.biz.dao.services;


import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.ImagenProducto;
import tip.java.barraca_lenia.biz.dao.entities.Presentacion;
import tip.java.barraca_lenia.biz.dao.entities.Producto;
import tip.java.barraca_lenia.biz.dao.repositories.ImagenProductoRepository;
import tip.java.barraca_lenia.biz.dao.repositories.PresentacionRepository;
import tip.java.barraca_lenia.biz.dao.repositories.ProductoRepository;
import tip.java.barraca_lenia.biz.dao.repositories.UsuarioRepository;
import tip.java.barraca_lenia.dto.ImagenProductoDTO;
import tip.java.barraca_lenia.dto.UsuarioDTO;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class ImagenProductoService {


    private final ImagenProductoRepository imagenProductoRepository;
    private final ProductoRepository productoRepository;
    private final PresentacionRepository presentacionRepository;


    @CacheEvict(value = "imagenes",  allEntries = true)
    public ImagenProductoDTO crearImagenProducto(ImagenProductoDTO dto) {

        Presentacion presentacion = presentacionRepository.findById(dto.getIdPresentacion())
                .orElseThrow(() -> new RuntimeException("Presentación no encontrada"));



        ImagenProducto imagen = new ImagenProducto();


        imagen.setNombre(dto.getNombre());
        imagen.setExtension(dto.getExtension());
        imagen.setImagen(dto.getImagen());
        imagen.setEsPrincipal(dto.getEsPrincipal());
        imagen.setPresentacion(presentacion);
        imagen.setProducto(presentacion.getProducto());

        ImagenProducto guardada = imagenProductoRepository.save(imagen);

        return mapeo(guardada);
    }

    @Cacheable("imagenes")
    public List<ImagenProductoDTO> listarImagenes() {
        return imagenProductoRepository.findAllConProductoYPresentacion()
                .stream()
                .map(this::mapeo)
                .toList();
    }

    public List<ImagenProductoDTO> listarPorProducto(Long idProducto) {
        return imagenProductoRepository.findByProductoId(idProducto)
                .stream()
                .map(this::mapeo)
                .toList();
    }

    public ImagenProductoDTO obtenerImagenPorPresentacion(Long idPresentacion) {

        ImagenProducto imagen = imagenProductoRepository
                .findFirstByPresentacionId(idPresentacion)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));

        return mapeo(imagen);
    }

    private ImagenProductoDTO mapeo(ImagenProducto imagenProducto) {
        ImagenProductoDTO imagenProductoDTO = new ImagenProductoDTO();
        imagenProductoDTO.setId(imagenProducto.getId());
        imagenProductoDTO.setNombre(imagenProducto.getNombre());
        imagenProductoDTO.setExtension(imagenProducto.getExtension());
        imagenProductoDTO.setImagen(imagenProducto.getImagen());
        imagenProductoDTO.setEsPrincipal(imagenProducto.getEsPrincipal());
        if (imagenProducto.getProducto() != null) {
            imagenProductoDTO.setIdProducto(imagenProducto.getProducto().getId());
        }
        if (imagenProducto.getPresentacion() != null) {
            imagenProductoDTO.setIdPresentacion(imagenProducto.getPresentacion().getId());
        }
        return imagenProductoDTO;

    }


}

package tip.java.barraca_lenia.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

public class ImagenProductoDTO {

    private Long id;
    private String nombre;
    private String extension;
    private String imagen;
    private Boolean esPrincipal;
    private Long idProducto;
    private Long idPresentacion;

}

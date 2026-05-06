package tip.java.barraca_lenia.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

public class ProductoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipoUso;
    private Boolean activo;

}

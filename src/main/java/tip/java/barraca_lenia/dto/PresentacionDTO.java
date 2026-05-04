package tip.java.barraca_lenia.dto;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

public class PresentacionDTO {

    private Long id;
    private String descripcion;
    private Integer cantidad;
    private String unidadMedida;
    private BigDecimal precio;
    private Long idProducto;


}

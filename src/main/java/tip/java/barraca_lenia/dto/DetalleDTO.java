package tip.java.barraca_lenia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleDTO {


    private Long idDetalle;
    private Long idPresentacion;

    private Integer cantidad;
    private Float subtotal;

}

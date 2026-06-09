package tip.java.barraca_lenia.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DireccionDTO {

    private Long id;
    private Long idUsuario;
    private String calle;
    private Integer numeroCasa;
    private String referencia;
    private String alias;

}

package tip.java.barraca_lenia.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EstadoDTO {

    private Long id;
    private String estado;

}

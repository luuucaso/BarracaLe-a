package tip.java.barraca_lenia.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

public class NotificacionDTO {

    private Long id;
    private String mensaje;
    private LocalDateTime fechaHora;
    private boolean leido;
    private int idPedido;
    private Long idUsuario;

}

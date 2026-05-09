package tip.java.barraca_lenia.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PedidoDTO {
    private Integer idPedido;
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaEntrega;
    private String horarioEntrega;
    private Float precioTotal;

    private Long idUsuario;
    private Long idDireccion;
    private Long idEstado;

    private List<DetalleDTO> detalles;
}

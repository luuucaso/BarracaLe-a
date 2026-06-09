package tip.java.barraca_lenia.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PedidoDTO {
    private Integer idPedido;
    private LocalDate fechaPedido;
    private LocalDate fechaEntrega;
    private String horarioEntrega;
    private Float precioTotal;

    private Long idUsuario;
    private Long idDireccion;
    private Long idEstado;

    private String nombreCliente;
    private String telefonoCliente;
    private String estado;

    private String calle;
    private Integer numeroCasa;
    private String referencia;

    private List<DetalleDTO> detalles;

    private ClienteAnonimoDTO clienteAnonimo;
}

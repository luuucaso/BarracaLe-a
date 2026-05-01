package tip.java.barraca_lenia.biz.dao.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "detalles_pedidos")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_detalle_pedido")
    private Integer id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "id_presentacion")
    private Presentacion presentacion;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

}

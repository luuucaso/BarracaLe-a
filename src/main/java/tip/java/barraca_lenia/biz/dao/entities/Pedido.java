package tip.java.barraca_lenia.biz.dao.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_pedido")
    private Long id;

    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;

    @Column(name="fecha_hora_entrega")
    private LocalDateTime fechaHoraEntrega;

    @Column(name="precio_total")
    private BigDecimal precioTotal;

    @ManyToOne
    @JoinColumn(name = "id_cliente_anonimo")
    private ClienteAnonimo clienteAnonimo;

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detallePedidos;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

    @OneToMany(mappedBy = "pedido")
    private List<Notificacion> notificaciones;

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;


}

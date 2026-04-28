package tip.java.barraca_lenia.biz.dao.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_direccion")
    private Integer id;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numero_casa")
    private Integer numeroCasa;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "Alias")
    private String alias;

    @OneToMany(mappedBy = "direccion")
    private List<Pedido> pedidos;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}

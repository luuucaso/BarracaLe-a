package tip.java.barraca_lenia.biz.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long id;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numero_casa")
    private Integer numeroCasa;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "alias")
    private String alias;

    @JsonIgnore
    @OneToMany(mappedBy = "direccion")
    private List<Pedido> pedidos;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
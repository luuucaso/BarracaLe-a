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
@Table(name="clientes_anonimos")
public class ClienteAnonimo {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    @Column(name = "id_cliente_anonimo")
    private Long id;

    //ver si ponerlo o no, c
    @Column(name ="token",unique=true)
    private String token;

    @Column(name="nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "calle")
    private String calle;

    @Column(name="numero_casa")
    private String numeroCasa;

    @Column(name="referencia")
    private String referencia;

    @OneToMany(mappedBy = "cliente_anonimo")
    private List<Pedido> pedidos;



}

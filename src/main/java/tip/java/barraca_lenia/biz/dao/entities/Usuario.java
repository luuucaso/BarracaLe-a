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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "password")
    private String password;

    @Column(name = "rut")
    private String rut;

    //faltarolusuario

//    @OneToMany(mappedBy = "direccion")
//    private List<Direccion> direcciones;


    public enum Rol {
        ADMIN,
        CLIENTE,
        EMPLEADO
    }

    @Enumerated(EnumType.STRING)
    private Rol rol;

}

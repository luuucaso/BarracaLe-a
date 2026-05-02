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
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    @Column(name = "id_producto")
    private Long id;


    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo_uso")
    private String tipoUso;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "producto")
    private List<ImagenProducto> imagenesProductos;

    @OneToMany(mappedBy = "producto")
    private List<Presentacion> presentaciones;
}

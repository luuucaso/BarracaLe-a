package tip.java.barraca_lenia.biz.dao.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.lang.model.element.ModuleElement;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "imagenes_productos")
public class ImagenProducto {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    @Column(name = "id_imagen_producto")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "extension")
    private String extension;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imagen;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

}




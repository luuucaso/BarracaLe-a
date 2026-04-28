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
@Table(name = "imagenes_prodictos")
public class ImagenProducto {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    @Column(name = "id_imagen_producto")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

}

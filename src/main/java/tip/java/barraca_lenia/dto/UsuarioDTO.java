package tip.java.barraca_lenia.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tip.java.barraca_lenia.biz.dao.entities.Pedido;
import tip.java.barraca_lenia.biz.dao.entities.RolUsuario;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor



//preguntar al profe si usar un DTO para request o otro para response
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String telefono;
    private String password;
    private String rut;

}

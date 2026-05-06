package tip.java.barraca_lenia.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tip.java.barraca_lenia.biz.dao.entities.Pedido;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

public class ClienteAnonimoDTO {

    private Long id;
    private String nombre;
    private String telefono;
    private String calle;
    private String numeroCasa;
    private  String referencia;
}

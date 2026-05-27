package tip.java.barraca_lenia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private String nombre;
    private String telefono;
    private List<String> roles;
    private Long idUsuario;
}

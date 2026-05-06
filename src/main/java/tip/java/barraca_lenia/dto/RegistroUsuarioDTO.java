package tip.java.barraca_lenia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    private String nombre;
    private String telefono;
    private String password;
    private String rut;
    private String rol; // CLIENTE o EMPRESA / ADMIN
}
package tip.java.barraca_lenia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroResponseDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private List<String> roles;
}

package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Rol;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.repositories.RolRepository;
import tip.java.barraca_lenia.dto.RolDTO;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    //crear rol
    public RolDTO crearRol(RolDTO rolDTO){
        Optional<Rol> existente = rolRepository.findByNombre(rolDTO.getNombre());

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un rol con ese nombre.");
        }

        Rol rol = new Rol();
        rol.setNombre(rolDTO.getNombre());

        Rol guardado = rolRepository.save(rol);

        return mapeo(guardado);

    }

    //actualizar rol
    public RolDTO actualizarRol(Long id, RolDTO rolDTO) {
        Optional<Rol> r = rolRepository.findById(id);

        if(r.isEmpty()){
            throw new RuntimeException("Rol no encontrado con id: " + id);
        }

        Rol rol = r.get();
        rol.setNombre(rolDTO.getNombre());
        Rol actualizado = rolRepository.save(rol);
        return mapeo(actualizado);
    }
    //borrar rol
    public void borrarRol(Long id){
        Optional<Rol> r = rolRepository.findById(id);

        if(r.isEmpty()){
            throw new RuntimeException("Rol no encontrado con id: "+ id);
        }
        rolRepository.delete(r.get());

    }

    //listar roles
    public List<RolDTO> listarRoles()
    {
        return rolRepository.findAll().stream().map(rol->{
            RolDTO r = new RolDTO();
            r.setId(rol.getId());
            r.setNombre(rol.getNombre());
            return r;
        }).toList();
    }


    private RolDTO mapeo(Rol rol){
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(rol.getId());
        rolDTO.setNombre(rol.getNombre());
        return rolDTO;
    }


}

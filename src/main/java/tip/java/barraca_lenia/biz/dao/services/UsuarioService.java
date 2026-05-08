package tip.java.barraca_lenia.biz.dao.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Rol;
import tip.java.barraca_lenia.biz.dao.entities.RolUsuario;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.repositories.RolRepository;
import tip.java.barraca_lenia.biz.dao.repositories.RolUsuarioRepository;
import tip.java.barraca_lenia.biz.dao.repositories.UsuarioRepository;
import tip.java.barraca_lenia.dto.RegistroUsuarioDTO;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final RolUsuarioRepository rolUsuarioRepository;

    public UsuarioDTO crearUsuario( UsuarioDTO usuarioDTO) {

        Optional<Usuario> existente = usuarioRepository.findByTelefono(usuarioDTO.getTelefono());

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese teléfono");
        }

        Usuario usuario = new Usuario();

            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setPassword(usuarioDTO.getPassword());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuario.setRut(usuarioDTO.getRut());
            Usuario guardado = usuarioRepository.save(usuario);

            return mapeo(guardado);

    }

    public Usuario registrar(RegistroUsuarioDTO dto) {

        Optional<Usuario> existente = usuarioRepository.findByTelefono(dto.getTelefono());

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese teléfono");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setTelefono(dto.getTelefono());
        usuario.setPassword(dto.getPassword());
        usuario.setRut(dto.getRut());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Rol rol = rolRepository.findByNombre(dto.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        RolUsuario rolUsuario = new RolUsuario();
        rolUsuario.setUsuario(usuarioGuardado);
        rolUsuario.setRol(rol);

        rolUsuarioRepository.save(rolUsuario);

        return usuarioGuardado;
    }



    public void borrarUsuario(String telefono){
        Optional<Usuario> u = usuarioRepository.findByTelefono(telefono);

        if(u.isEmpty()){
            throw new RuntimeException("Usuario no encontrado con teléfono: "+ telefono);
        }
        usuarioRepository.delete(u.get());

    }

    public UsuarioDTO actualizarUsuario(String telefono, UsuarioDTO usuarioDTO) {
        Optional<Usuario> u = usuarioRepository.findByTelefono(telefono);

        if(u.isEmpty()){
            throw new RuntimeException("Usuario no encontrado con télefono: " + telefono);
        }

            Usuario usuario = u.get();
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuario.setPassword(usuarioDTO.getPassword());
            usuario.setRut(usuarioDTO.getRut());

            Usuario actualizado = usuarioRepository.save(usuario);
            return mapeo(actualizado);
    }

    public List<UsuarioDTO> listarUsuarios()
    {
        return usuarioRepository.findAll().stream().map(usuario->{
            UsuarioDTO u = new UsuarioDTO();
            u.setNombre(usuario.getNombre());
            u.setId(usuario.getId());
            u.setTelefono(usuario.getTelefono());
            u.setPassword(usuario.getPassword());
            u.setRut(usuario.getRut());
            return u;
        }).toList();
    }

    private UsuarioDTO mapeo(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setRut(usuario.getRut());
        return usuarioDTO;

    }

}

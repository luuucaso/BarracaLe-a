package tip.java.barraca_lenia.biz.dao.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.repositories.ProductoRepository;
import tip.java.barraca_lenia.biz.dao.repositories.UsuarioRepository;
import tip.java.barraca_lenia.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public Usuario crearUsuario( UsuarioDTO usuarioDTO) {

        Optional<Usuario> u = usuarioRepository.findByTelefono(usuarioDTO.getTelefono());


        if (u.isPresent()) {
            Usuario usuario = new Usuario();

            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setPassword(usuarioDTO.getPassword());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuario.setRut(usuarioDTO.getRut());
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("El usuario ya existe");
    }

    public boolean borrarUsuario(String telefono){
        Optional<Usuario> u = usuarioRepository.findByTelefono(telefono);
        if(u.isPresent()){
            usuarioRepository.delete(u.get());
            return true;
        }
        return false;

    }

    public Usuario actualizarUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> u = usuarioRepository.findByTelefono(usuarioDTO.getTelefono());
        if(u.isPresent()){
            Usuario usuario = u.get();
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuario.setPassword(usuarioDTO.getPassword());
            usuario.setRut(usuarioDTO.getRut());

            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public List<UsuarioDTO> listarUsuarios()
    {
        return usuarioRepository.findAll().stream().map(usuario->{
            UsuarioDTO u = new UsuarioDTO();
            u.setNombre(usuario.getNombre());
            u.setId(usuario.getId());
            u.setTelefono(usuario.getTelefono());
            u.setRut(usuario.getRut());
            return u;
        }).toList();
    }
}

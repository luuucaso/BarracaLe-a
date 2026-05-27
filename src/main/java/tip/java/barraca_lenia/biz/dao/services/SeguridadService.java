package tip.java.barraca_lenia.biz.dao.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.repositories.UsuarioRepository;
import tip.java.barraca_lenia.dto.LoginResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeguridadService {

    private final UsuarioRepository usuarioRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Transactional(readOnly = true)
    public Optional<Usuario> login(String telefono, String password) {
        return usuarioRepository.findByTelefonoAndPassword(telefono, password);
    }

    public Optional<Usuario> buscarPorTelefono(String telefono) {
        return usuarioRepository.findByTelefono(telefono);
    }

    public LoginResponseDTO crearRespuestaLogin(Usuario usuario) {
        LoginResponseDTO respuesta = new LoginResponseDTO();
        respuesta.setToken(generarToken(usuario));
        respuesta.setNombre(usuario.getNombre());
        respuesta.setTelefono(usuario.getTelefono());
        respuesta.setRoles(obtenerRoles(usuario));
        respuesta.setIdUsuario(usuario.getId());
        return respuesta;
    }

    public List<String> obtenerRoles(Usuario usuario) {
        return usuario.getRolUsuarios().stream()
                .map(ru -> ru.getRol().getNombre())
                .collect(Collectors.toList());
    }

    public String generarToken(Usuario usuario) {
        String[] rolesBd = usuario.getRolUsuarios().stream()
                .map(u -> u.getRol().getNombre())
                .toArray(String[]::new);
        List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(rolesBd);

        return Jwts.builder()
                .setSubject(usuario.getTelefono())
                .claim("nombre", usuario.getNombre())
                .claim("authorities", roles.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
    }
}

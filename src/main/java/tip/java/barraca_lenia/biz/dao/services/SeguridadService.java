package tip.java.barraca_lenia.biz.dao.services;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import tip.java.barraca_lenia.biz.dao.entities.Usuario;
import tip.java.barraca_lenia.biz.dao.repositories.UsuarioRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class SeguridadService {

    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> login(String telefono, String password){
        Optional<Usuario> usuarioBd = usuarioRepository.findByTelefonoAndPassword(telefono, password);
        if(usuarioBd.equals(null)){
            return Optional.empty();
        }
        return usuarioBd;
    }

    public String generarToken(Usuario usuario){
        String clave = "TIP2026";
        String[] rolesBd = usuario.getRolUsuarios().stream().map(
                u -> u.getRol().getNombre()).toArray(String[]::new);
        List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(rolesBd);

        String token = Jwts
                .builder()
                .setId("TIP2026")
                .setSubject(usuario.getNombre())
                .claim("authorities",
                        roles.stream()
                                .map(g->g.getAuthority())
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+300000))
                .signWith(SignatureAlgorithm.HS256, clave.getBytes())
                .compact();

        return token;


    }
}

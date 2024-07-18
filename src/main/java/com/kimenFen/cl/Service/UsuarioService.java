package com.kimenFen.cl.Service;

import com.kimenFen.cl.Model.Modulo11;
import com.kimenFen.cl.Model.Usuario;
import com.kimenFen.cl.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public UsuarioService(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    public void createUser(String rut, String role) {
        String cleanRut = rut.replace(".", "").replace("-", "");
        String rutSinDv = Modulo11.rutDV(cleanRut);

        Usuario usuario = new Usuario(cleanRut, rutSinDv);

        // Guardar usuario en MongoDB
        usuarioRepository.save(usuario);

        // Crear usuario en InMemoryUserDetailsManager para autenticación en memoria
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(cleanRut)
                .password(rutSinDv)
                .roles(role)
                .build();
        inMemoryUserDetailsManager.createUser(user);

        System.out.println("Usuario creado: " + cleanRut);
        System.out.println("Contraseña: " + rutSinDv);
    }
}

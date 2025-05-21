package com.test.prueba1.service;

import com.test.prueba1.entity.Telefono;
import com.test.prueba1.entity.Usuario;
import com.test.prueba1.repository.UsuarioRepository;
import com.test.prueba1.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Relacionar teléfonos con el usuario
        if (usuario.getPhones() != null) {
            usuario.getPhones().forEach(t -> t.setUsuario(usuario));
        }

        // Generar fechas
        Date now = new Date();
        usuario.setCreated(now);
        usuario.setModified(now);
        usuario.setLastLogin(now);
        usuario.setIsActive(true);

        // Generar token JWT
        String token = jwtService.generateToken(usuario.getEmail());
        usuario.setToken(token);

        return usuarioRepository.save(usuario);
    }
}

package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Usuario;
import com.deleondiego.kinalapp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORTANTE
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);

        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);

        if ("admin".equalsIgnoreCase(usuario.getUserName())) {
            usuario.setRol("ADMIN");
        } else {
            usuario.setRol("USER");
        }
        usuario.setEstado(1L);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorCodigoUsuario(Long codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    public Usuario actualizar(Long codigoUsuarioViejo, Usuario usuarioNuevo) {
        if (!usuarioRepository.existsById(codigoUsuarioViejo)) {
            throw new RuntimeException("No existe un usuario con ese codigo: " + codigoUsuarioViejo);
        }

        validarUsuario(usuarioNuevo);

        // Si el usuario cambia la contraseña al editar, también deberías encriptarla aquí
        if (usuarioNuevo.getPassword() != null && !usuarioNuevo.getPassword().startsWith("$2a$")) {
            usuarioNuevo.setPassword(passwordEncoder.encode(usuarioNuevo.getPassword()));
        }

        if (!codigoUsuarioViejo.equals(usuarioNuevo.getCodigoUsuario())) {
            usuarioRepository.deleteById(codigoUsuarioViejo);
        }

        return usuarioRepository.save(usuarioNuevo);
    }

    @Override
    public void eliminar(Long codigoUsuario) {
        if (!usuarioRepository.existsById(codigoUsuario)) {
            throw new RuntimeException("No se encontró al usuario con ese código: " + codigoUsuario);
        }
        usuarioRepository.deleteById(codigoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoUsuario(Long codigoUsuario) {
        return usuarioRepository.existsById(codigoUsuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El objeto Usuario no puede ser nulo");
        }
        if (usuario.getUserName() == null || usuario.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es un dato obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es un dato obligatorio ");
        }
    }
}
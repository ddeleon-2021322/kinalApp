package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Cliente;
import com.deleondiego.kinalapp.entity.Usuario;
import com.deleondiego.kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService{
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setPassword("{noop}" + usuario.getPassword());
        if ("admin".equalsIgnoreCase(usuario.getUserName())) {
            usuario.setRol("ADMIN");
        } else {
            usuario.setRol("USER");
        }
        usuario.setEstado(1L);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorCodigoUsuario(Long codigoUsuario){
        return usuarioRepository.findById(codigoUsuario);
    }

    @Override
    public Usuario actualizar(Long codigoUsuarioViejo, Usuario usuarioNuevo) {
        if (!usuarioRepository.existsById(codigoUsuarioViejo)) {
            throw new RuntimeException("No existe un usuario con ese codigo: " + codigoUsuarioViejo);
        }

        validarUsuario(usuarioNuevo);


        if (!codigoUsuarioViejo.equals(usuarioNuevo.getCodigoUsuario())) {
            // Eliminamos el registro con el ID viejo
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


    private void validarUsuario(Usuario usuario){

        if (usuario == null ){
            throw new IllegalArgumentException("El objeto Usuario no puede ser nulo");
        }
        if (usuario.getUserName() == null || usuario.getUserName().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es un dato obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("La contraseña es un dato obligatorio ");
        }

    }
}

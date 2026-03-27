package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarUsuarios();
    Usuario guardar(Usuario usuario);
    Optional <Usuario> buscarPorCodigoUsuario(Long codigoUsuario);
    Usuario actualizar (Long codigoUsuario, Usuario usuario);
    void eliminar (Long codigoUsuario);
    boolean existePorCodigoUsuario(Long codigoUsuario);


}

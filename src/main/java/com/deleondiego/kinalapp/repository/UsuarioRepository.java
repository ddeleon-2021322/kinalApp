package com.deleondiego.kinalapp.repository;

import com.deleondiego.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, String> {
}
    
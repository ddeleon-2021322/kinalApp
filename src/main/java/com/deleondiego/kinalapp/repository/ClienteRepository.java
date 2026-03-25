package com.deleondiego.kinalapp.repository;

import com.deleondiego.kinalapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente,String> {
}

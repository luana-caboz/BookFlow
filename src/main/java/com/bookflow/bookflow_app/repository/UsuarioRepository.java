package com.bookflow.bookflow_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.bookflow_app.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {

    Optional<Usuario> findByCpf(String cpf);

}

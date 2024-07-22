package com.Descomplica.CRUD_Farmacia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Descomplica.CRUD_Farmacia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByUsuario(String usuario);
}

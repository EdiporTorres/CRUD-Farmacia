package com.Descomplica.CRUD_Farmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Descomplica.CRUD_Farmacia.model.Produto;

public interface ProdutoRepository  extends JpaRepository<Produto, Long>{
	public List<Produto> findAllByNomeProdutoContainingIgnoreCase(String nomeProduto);

}

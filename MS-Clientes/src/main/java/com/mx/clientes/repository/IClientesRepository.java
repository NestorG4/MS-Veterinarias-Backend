package com.mx.clientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.clientes.domain.Clientes;

public interface IClientesRepository extends JpaRepository<Clientes, Long> {
	
	public List<Clientes> findByVeterinariaId(Long veterinariaId); 
}

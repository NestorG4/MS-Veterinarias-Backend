package com.mx.responsables.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.responsables.domain.Responsables;

public interface IResponsablesRepository extends JpaRepository<Responsables, Long>{
	
	public List<Responsables> findByVeterinariaId(Long veterinariaId); 
}

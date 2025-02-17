package com.mx.mascotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mx.mascotas.domain.Mascotas;

public interface IMascotasRepository extends JpaRepository<Mascotas, Long>{
	
	public List<Mascotas> findByVeterinariaId(Long veterinariaId); 

}

package com.mx.responsables.service;

import java.util.List;
import java.util.Optional;

import com.mx.responsables.domain.Responsables;

public interface IResponsablesService {
	
	public List<Responsables> listar();
	
	public Responsables guardarResponsables(Responsables responsables); 
	
	public Optional<Responsables> buscarResponsables(Long idResponsable); 
	
	public void eliminarResponsables(Long idResponsable);
}

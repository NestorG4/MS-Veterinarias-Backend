package com.mx.centroveterinario.service;

import java.util.List;
import java.util.Optional;

import com.mx.centroveterinario.domain.Veterinarias;

public interface VeterinariaService {
	
	public List<Veterinarias> veterinarias(); 
	
	public Veterinarias guardarVeterinaria(Veterinarias veterinarias);
	
	public Optional<Veterinarias> buscarVeterinaria(Long idVeterinaria); 
	
	public void eliminarVeterinaria(Long idVeterinaria); 
}

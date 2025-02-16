package com.mx.centroveterinario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mx.centroveterinario.domain.Veterinarias;
import com.mx.centroveterinario.repository.IVeterinariaRepository;

@Service
public class VeterinariasServiceImp implements VeterinariaService {
	
	@Autowired
	private IVeterinariaRepository repository; 

	@Override
	public List<Veterinarias> veterinarias() {
		return repository.findAll(Sort.by(Direction.ASC, "idVeterinaria")); 
	}

	@Override
	public Veterinarias guardarVeterinaria(Veterinarias veterinarias) {
		return repository.save(veterinarias); 
	}

	@Override
	public Optional<Veterinarias> buscarVeterinaria(Long idVeterinaria) {
		return repository.findById(idVeterinaria); 
		
	}

	@Override
	public void eliminarVeterinaria(Long idVeterinaria) {
		Veterinarias veterinaria = buscarVeterinaria(idVeterinaria)
				.orElseThrow(()-> new RuntimeException("Veterinaria no encontrada: " + idVeterinaria));
		repository.delete(veterinaria);
	}

}

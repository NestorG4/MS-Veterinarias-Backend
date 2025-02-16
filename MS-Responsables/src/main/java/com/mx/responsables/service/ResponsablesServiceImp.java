package com.mx.responsables.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mx.responsables.domain.Responsables;
import com.mx.responsables.repository.IResponsablesRepository;

@Service
public class ResponsablesServiceImp implements IResponsablesService{
	
	@Autowired
	private IResponsablesRepository repository; 

	@Override
	public List<Responsables> listar() {
		return repository.findAll(Sort.by(Direction.ASC, "idResponsable")); 
	}

	@Override
	public Responsables guardarResponsables(Responsables responsables) {
		return repository.save(responsables); 
	}

	@Override
	public Optional<Responsables> buscarResponsables(Long idResponsable) {
		return Optional.ofNullable(repository.findById(idResponsable))
				.orElseThrow(()-> new RuntimeException("Cliente no encontrado con ID: " + idResponsable));
	}

	@Override
	public void eliminarResponsables(Long idResponsable) {
		Optional<Responsables> responsable = buscarResponsables(idResponsable);
		responsable.ifPresent(responsables -> repository.delete(responsables));
		
	}

}

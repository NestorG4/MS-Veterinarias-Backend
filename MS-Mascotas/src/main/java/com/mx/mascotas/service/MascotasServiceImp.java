package com.mx.mascotas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mx.mascotas.domain.Mascotas;
import com.mx.mascotas.repository.IMascotasRepository;

@Service
public class MascotasServiceImp implements IMascotasService {
	
	@Autowired
	private IMascotasRepository repository;
	@Override
	public List<Mascotas> listar() {
		return repository.findAll(Sort.by(Direction.ASC, "idMascota")); 
	}

	@Override
	public Mascotas guardarMascotas(Mascotas mascotas) {
		return repository.save(mascotas); 
	}

	@Override
	public Optional<Mascotas> buscarMascota(Long idMascotas) {
		return Optional.ofNullable(repository.findById(idMascotas))
				.orElseThrow(() -> new RuntimeException("Mascota no encontradaa con ID: "+ idMascotas)); 
	}

	@Override
	public void eliminarMascotas(Long idMascotas) {
		Optional<Mascotas> mascota = buscarMascota(idMascotas); 
		mascota.ifPresent(mascotas -> repository.delete(mascotas));
		
	}
	
	public List<Mascotas> getByVeterinariaId(Long veterinariaId){
		return repository.findByVeterinariaId(veterinariaId); 
	}


}

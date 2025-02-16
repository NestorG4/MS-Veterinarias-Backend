package com.mx.mascotas.service;

import java.util.List;
import java.util.Optional;

import com.mx.mascotas.domain.Mascotas;

public interface IMascotasService {
	
	public List<Mascotas> listar();
	
	public Mascotas guardarMascotas(Mascotas mascotas);

	public Optional<Mascotas> buscarMascota(Long idMmascotas);
	
	public void eliminarMascotas(Long idMascotas);
	
}

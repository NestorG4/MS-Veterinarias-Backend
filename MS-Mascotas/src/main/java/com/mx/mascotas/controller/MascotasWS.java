package com.mx.mascotas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.mascotas.domain.Mascotas;
import com.mx.mascotas.service.MascotasServiceImp;

@RestController
@RequestMapping("Mascotas")
@CrossOrigin("*")
public class MascotasWS {
	
	@Autowired
	private MascotasServiceImp service; 
	
	@GetMapping("/listar")
	public ResponseEntity<List<Mascotas>> listar(){
		List<Mascotas> mascotas = service.listar(); 
		return Optional.of(mascotas)
				.filter(m -> !m.isEmpty())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build()); 
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Mascotas> guardarMascotas(@RequestBody Mascotas mascotas){
		return Optional.ofNullable(service.guardarMascotas(mascotas))
				.map(m -> ResponseEntity.status(HttpStatus.CREATED).body(m))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); 
	
	}
	
	@PostMapping("/buscar/{idMascota}")
	public ResponseEntity<Mascotas> buscarMascotas(@PathVariable Long idMascota){
		return service.buscarMascota(idMascota)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
	}
	
	@DeleteMapping("/eliminar/{idMascota}")
	public ResponseEntity<?> eliminarMascotas(@PathVariable Long idMascota){
		return service.buscarMascota(idMascota).map(mascotas -> {
					service.eliminarMascotas(idMascota); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
				})
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}

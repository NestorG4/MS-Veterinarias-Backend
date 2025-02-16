package com.mx.centroveterinario.controller;

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

import com.mx.centroveterinario.domain.Veterinarias;
import com.mx.centroveterinario.service.VeterinariasServiceImp;

@RestController
@RequestMapping("Veterinarias")
@CrossOrigin("*")
public class VeterinariasWS {
	
	@Autowired
	private VeterinariasServiceImp service;
	
	@GetMapping("listar")
	public ResponseEntity<List<Veterinarias>> listar(){
		List<Veterinarias> veterinarias = service.veterinarias();
		return ResponseEntity.of(veterinarias.isEmpty() ? Optional.empty() : Optional.of(veterinarias)); 
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Veterinarias> guardarVeterinaria(@RequestBody Veterinarias veterinarias){
		return Optional.ofNullable(service.guardarVeterinaria(veterinarias))
				.map(v -> ResponseEntity.status(HttpStatus.CREATED).body(v))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); 
		 
	}
	
	@PostMapping("/buscar/{idVeterinaria}")
	public ResponseEntity<Veterinarias> buscarVeterinaria(@PathVariable Long idVeterinaria){
		return service.buscarVeterinaria(idVeterinaria)
				.map(ResponseEntity::ok)
				.orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/eliminar/{idVeterinaria}")
	public ResponseEntity<?> eliminarVeterinaria(@PathVariable Long idVeterinaria){
		return service.buscarVeterinaria(idVeterinaria)
				.map(veterinaria -> {
					service.eliminarVeterinaria(idVeterinaria); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
				})
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se encontró la veterinaria con ID: " + idVeterinaria)); 
	}
}

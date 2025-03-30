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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("Mascotas")
@Tag(name = "Mascotas", description = "Gestion de mascotas")
public class MascotasWS {
	
	@Autowired
	private MascotasServiceImp service; 
	
	@GetMapping("/listar")
	@Operation(summary = "Listar mascotas", description = "Retorna una lista de todas las mascotas registradas.")
	public ResponseEntity<List<Mascotas>> listar(){
		List<Mascotas> mascotas = service.listar(); 
		return Optional.of(mascotas)
				.filter(m -> !m.isEmpty())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build()); 
	}
	
	@PostMapping("/guardar")
	@Operation(summary = "Guardar una mascota", description = "Recibe un objeto Mascota y lo guarda en la base de datos.")
	public ResponseEntity<Mascotas> guardarMascotas(@RequestBody Mascotas mascotas){
		return Optional.ofNullable(service.guardarMascotas(mascotas))
				.map(m -> ResponseEntity.status(HttpStatus.CREATED).body(m))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); 
	
	}
	
	@PostMapping("/buscar/{idMascota}")
	@Operation(summary = "Buscar una mascota por ID", description = "Recibe el ID de una mascota y la retorna si fue encontrada.")
	public ResponseEntity<Mascotas> buscarMascotas(@PathVariable Long idMascota){
		return service.buscarMascota(idMascota)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
	}
	
	@DeleteMapping("/eliminar/{idMascota}")
	@Operation(summary = "Eliminar una mascota", description = "Recibe el ID de una mascota y la elimina si existe.")
	public ResponseEntity<?> eliminarMascotas(@PathVariable Long idMascota){
		return service.buscarMascota(idMascota).map(mascotas -> {
					service.eliminarMascotas(idMascota); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
				})
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("veterinaria/{veterinariaId}")
	@Operation(summary = "Obtener mascotas por veterinaria", description = "Recibe el ID de una veterinaria y retorna la lista de mascotas asociadas.")
	public ResponseEntity<List<Mascotas>> obtenerPorVeterinariaId(@PathVariable Long veterinariaId){
		return ResponseEntity.status(HttpStatus.OK).body(service.getByVeterinariaId(veterinariaId));
	}
}

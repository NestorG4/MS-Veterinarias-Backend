package com.mx.responsables.controller;

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

import com.mx.responsables.domain.Responsables;
import com.mx.responsables.service.ResponsablesServiceImp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("Responsables")
@Tag(name = "Mascotas", description = "Gestion de mascotas")
public class ResponsableWS {

	@Autowired
	private ResponsablesServiceImp service;

	@GetMapping("/listar")
	@Operation(summary = "Listar responsables", description = "Retorna una lista de todos los responsables registrados.")
	public ResponseEntity<List<Responsables>> listar() {
		List<Responsables> responsables = service.listar();
		return Optional.of(responsables).filter(r -> !r.isEmpty()).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}

	@PostMapping("/guardar")
	@Operation(summary = "Guardar un responsable", description = "Recibe un objeto Responsable y lo guarda en la base de datos.")
	public ResponseEntity<Responsables> guardarResponsables(@RequestBody Responsables responsables) {
		return Optional.ofNullable(service.guardarResponsables(responsables))
				.map(r -> ResponseEntity.status(HttpStatus.CREATED).body(r))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PostMapping("/buscar/{idResponsable}")
	@Operation(summary = "Buscar un responsable por ID", description = "Recibe el ID de un responsable y lo retorna si fue encontrado.")
	public ResponseEntity<Responsables> buscarResponsables(@PathVariable Long idResponsable) {
		return service.buscarResponsables(idResponsable).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@DeleteMapping("/eliminar/{idResponsable}")
	@Operation(summary = "Eliminar un responsable", description = "Recibe el ID de un responsable y lo elimina si existe.")
	public ResponseEntity<?> eliminarResponsable(@PathVariable Long idResponsable) {
		return service.buscarResponsables(idResponsable).map(responsables -> {
			service.eliminarResponsables(idResponsable);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("veterinaria/{veterinariaId}")
	@Operation(summary = "Obtener responsables por veterinaria", description = "Recibe el ID de una veterinaria y retorna la lista de responsables asociados.")
	public ResponseEntity<List<Responsables>> obtenerPorVeterinariaId(@PathVariable Long veterinariaId){
		return ResponseEntity.status(HttpStatus.OK).body(service.getByVeterinariaId(veterinariaId));
	}
}

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

@RestController
@RequestMapping("Responsables")
public class ResponsableWS {

	@Autowired
	private ResponsablesServiceImp service;

	@GetMapping("/listar")
	public ResponseEntity<List<Responsables>> listar() {
		List<Responsables> responsables = service.listar();
		return Optional.of(responsables).filter(r -> !r.isEmpty()).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}

	@PostMapping("/guardar")
	public ResponseEntity<Responsables> guardarResponsables(@RequestBody Responsables responsables) {
		return Optional.ofNullable(service.guardarResponsables(responsables))
				.map(r -> ResponseEntity.status(HttpStatus.CREATED).body(r))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PostMapping("/buscar/{idResponsable}")
	public ResponseEntity<Responsables> buscarResponsables(@PathVariable Long idResponsable) {
		return service.buscarResponsables(idResponsable).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@DeleteMapping("/eliminar/{idResponsable}")
	public ResponseEntity<?> eliminarResponsable(@PathVariable Long idResponsable) {
		return service.buscarResponsables(idResponsable).map(responsables -> {
			service.eliminarResponsables(idResponsable);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("veterinaria/{veterinariaId}")
	public ResponseEntity<List<Responsables>> obtenerPorVeterinariaId(@PathVariable Long veterinariaId){
		return ResponseEntity.status(HttpStatus.OK).body(service.getByVeterinariaId(veterinariaId));
	}
}

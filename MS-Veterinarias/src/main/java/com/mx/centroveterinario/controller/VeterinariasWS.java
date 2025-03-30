package com.mx.centroveterinario.controller;

import java.util.List;
import java.util.Map;
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
import com.mx.centroveterinario.models.Clientes;
import com.mx.centroveterinario.models.Mascotas;
import com.mx.centroveterinario.models.Responsables;
import com.mx.centroveterinario.service.VeterinariasServiceImp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("Veterinarias")
@Tag(name= "Veterinarias", description = "Gestion de veterinarias")
public class VeterinariasWS {
	
	@Autowired
	private VeterinariasServiceImp service;
	
	@GetMapping("listar")
	@Operation(summary = "Enpoint que lista todas las veterinarias disponibles", 
				description = "Recupera la lista con todos las veterinarias registradas en el sistema")
	public ResponseEntity<List<Veterinarias>> listar(){
		List<Veterinarias> veterinarias = service.veterinarias();
		return Optional.of(veterinarias).filter(v -> !v.isEmpty()).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}
	
	@PostMapping("/guardar")
	@Operation(summary = "Guardar una veterinaria", 
				description = "Recibe un objeto de tipo veterinaria y lo guarda en la base de datos. Retorna la veterinaria guardada si la operacion fue exitosa")
	public ResponseEntity<Veterinarias> guardarVeterinaria(@RequestBody Veterinarias veterinarias){
		return Optional.ofNullable(service.guardarVeterinaria(veterinarias))
				.map(v -> ResponseEntity.status(HttpStatus.CREATED).body(v))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); 
		 
	}
	
	@PostMapping("/buscar/{idVeterinaria}")
	@Operation(summary = "Buscar una veterinaria", 
				description = "Recibe el ID de una veterinaria y la retorna si fue encontrada. Si no existe retorna codigo 404")
	public ResponseEntity<Veterinarias> buscarVeterinaria(@PathVariable Long idVeterinaria){
		return service.buscarVeterinaria(idVeterinaria)
				.map(ResponseEntity::ok)
				.orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/eliminar/{idVeterinaria}")
	@Operation(summary = "Elimina una veterinaria", 
				description = "Recibe el ID de una veterinaria, la elimina si fue encontra y responde con codigo 204."
						+ "Si no es encontrada, responde con un codigo 404")
	public ResponseEntity<?> eliminarVeterinaria(@PathVariable Long idVeterinaria){
		return service.buscarVeterinaria(idVeterinaria)
				.map(veterinaria -> {
					service.eliminarVeterinaria(idVeterinaria); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
				})
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No se encontró la veterinaria con ID: " + idVeterinaria)); 
	}
	
	//Endpoints pra FeignClient para responsables
	
	@PostMapping("/Resposables")
	@Operation(summary = "Guardar responsables", 
				description = "Recibe un objeto de tipo veterinarias y lo guarda en la base de datos")
	public ResponseEntity<Responsables> guardarResponsables(@RequestBody Responsables responsables){
		Responsables responsable = service.guardarResponsable(responsables);
		return ResponseEntity.status(HttpStatus.CREATED).body(responsable); 
	}
	
	@PostMapping("/Responsables/veterinaria/{veterinariaId}")
	@Operation(summary = "Obtener responsables por veterinaria", 
				description = "Recibe el ID de una veterinaria y retorna la lista de responsables asociados")
	public ResponseEntity<?> obtenerVeterinariasPorVeterinariasId(@PathVariable Long veterinariaId){
		List<Responsables> responsables = service.obtenerResponsables(veterinariaId); 
		if (responsables.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(responsables);
		}
	}
	
	//Endpoint para RestTemplate para Clientes
	@PostMapping("/Clientes")
	@Operation(summary = "Guardar un cliente", 
				description = "Recibe un objeto Cliente y lo guarda en la base de datos.")
	public ResponseEntity<?> guardarCliente(@RequestBody Clientes clientes){
		Clientes cliente = service.saveClientes(clientes); 
		if (clientes== null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(cliente);
		}
	}
	
	@PostMapping("/Clientes/veterinaria/{veterinariaId}")
	@Operation(summary = "Obtener clientes por veterinaria", 
				description = "Recibe el ID de una veterinaria y retorna la lista de clientes asociados.")
	public ResponseEntity<?> obtenerClientesPorTiendaId(@PathVariable Long veterinariaId){
		List<Clientes> clientes = service.getClientes(veterinariaId); 
		if (clientes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(clientes);
		}
	}
	
	//Endpoint para FeignClient de Mascotas
	@PostMapping("/Mascotas")
	@Operation(summary = "Guardar una mascota", 
				description = "Recibe un objeto Mascota y lo guarda en la base de datos.")
	public ResponseEntity<Mascotas> guardarMascotas(@RequestBody Mascotas mascotas){
		Mascotas mascota = service.guardarMascotas(mascotas);
		return ResponseEntity.status(HttpStatus.CREATED).body(mascota); 
	}
	
	@PostMapping("/Mascotas/veterinaria/{veterinariaId}")
	@Operation(summary = "Obtener mascotas por veterinaria", 
				description = "Recibe el ID de una veterinaria y retorna la lista de mascotas asociadas.")
	public ResponseEntity<?> obtenerMascotasPorVeterinariasId(@PathVariable Long veterinariaId){
		List<Mascotas> mascotas = service.obtenerMascotas(veterinariaId);
		if (mascotas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(mascotas);
		}
	}
	
	//**************Obtener Veterinarias completas
	@PostMapping("Datos/{tiendaId}")
	@Operation(summary = "Obtener datos completos de la veterinaria", 
				description = "Recibe el ID de una tienda y retorna toda la información relacionada.")
	public ResponseEntity<?> datosTienda(@PathVariable Long tiendaId){
		Map<String, Object> datos = service.datosVeterinaria(tiendaId); 
		return ResponseEntity.ok(datos);
	}
}

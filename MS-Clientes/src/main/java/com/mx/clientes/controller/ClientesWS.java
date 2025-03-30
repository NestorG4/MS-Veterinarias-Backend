package com.mx.clientes.controller;

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

import com.mx.clientes.domain.Clientes;
import com.mx.clientes.service.ClientesServiceImp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("Clientes")
@Tag(name = "Clientes", description = "Gestion de clientes")
public class ClientesWS {
	
	@Autowired
	private ClientesServiceImp service;
	
	@GetMapping("/listar")
	@Operation(summary = "Listar clientes", description = "Retorna una lista de todos los clientes registrados.")
	public ResponseEntity<List<Clientes>> listar(){
		List<Clientes> clientes = service.listar();
		return Optional.of(clientes)
	            .filter(c -> !c.isEmpty()) 
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.noContent().build()); 
	}
	
	@PostMapping("/guardar")
	@Operation(summary = "Guardar un cliente", description = "Recibe un objeto Cliente y lo guarda en la base de datos.")
	public ResponseEntity<Clientes> guardarClientes(@RequestBody Clientes clientes){
		return Optional.ofNullable(service.guardarClientes(clientes))
				.map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PostMapping("/buscar/{idCliente}")
	@Operation(summary = "Buscar un cliente por ID", description = "Recibe el ID de un cliente y lo retorna si fue encontrado.")
	public ResponseEntity<Clientes> buscarClientes(@PathVariable Long idCliente){
		return service.buscarClientes(idCliente)
				.map(ResponseEntity::ok)
				.orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/eliminar/{idCliente}")
	@Operation(summary = "Eliminar un cliente", description = "Recibe el ID de un cliente y lo elimina si existe.")
	public ResponseEntity<?> eliminarClientes(@PathVariable Long idCliente){
		return service.buscarClientes(idCliente)
				.map(veterinaria ->{
					service.eliminarClientes(idCliente); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.build());
	}
	
	@PostMapping("veterinaria/{veterinariaId}")
	@Operation(summary = "Obtener clientes por veterinaria", description = "Recibe el ID de una veterinaria y retorna la lista de clientes asociados.")
	public ResponseEntity<List<Clientes>> obtenerPorVeterinariaId(@PathVariable Long veterinariaId){
		return ResponseEntity.status(HttpStatus.OK).body(service.getByVeterinariaId(veterinariaId));
	}
}

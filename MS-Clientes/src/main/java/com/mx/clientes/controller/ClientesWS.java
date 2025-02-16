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

@RestController
@RequestMapping("Clientes")
@CrossOrigin("*")
public class ClientesWS {
	
	@Autowired
	private ClientesServiceImp service;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Clientes>> listar(){
		List<Clientes> clientes = service.listar();
		return Optional.of(clientes)
	            .filter(c -> !c.isEmpty()) 
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.noContent().build()); 
	}
	
	@PostMapping("/guardar")
	public ResponseEntity<Clientes> guardarClientes(@RequestBody Clientes clientes){
		return Optional.ofNullable(service.guardarClientes(clientes))
				.map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PostMapping("/buscar/{idCliente}")
	public ResponseEntity<Clientes> buscarClientes(@PathVariable Long idCliente){
		return service.buscarClientes(idCliente)
				.map(ResponseEntity::ok)
				.orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/eliminar/{idCliente}")
	public ResponseEntity<?> eliminarClientes(@PathVariable Long idCliente){
		return service.buscarClientes(idCliente)
				.map(veterinaria ->{
					service.eliminarClientes(idCliente); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND)
						.build());
	}
}

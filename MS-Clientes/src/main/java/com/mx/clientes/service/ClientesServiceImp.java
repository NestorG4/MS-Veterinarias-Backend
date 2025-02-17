package com.mx.clientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mx.clientes.domain.Clientes;
import com.mx.clientes.repository.IClientesRepository;

@Service
public class ClientesServiceImp implements IClientesService {

	@Autowired
	private IClientesRepository repository;

	@Override
	public List<Clientes> listar() {
		return repository.findAll(Sort.by(Direction.ASC, "idCliente"));
	}

	@Override
	public Clientes guardarClientes(Clientes clientes) {
		return repository.save(clientes);
	}

	@Override
	public Optional<Clientes> buscarClientes(Long idCliente) {
		return Optional.ofNullable(repository.findById(idCliente)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente)));
	}

	@Override
	public void eliminarClientes(Long idCliente) {
		Optional<Clientes> cliente = buscarClientes(idCliente);
		cliente.ifPresent(clientes -> repository.delete(clientes));
	}
	
	public List<Clientes> getByVeterinariaId(Long veterinariaId){
		return repository.findByVeterinariaId(veterinariaId); 
	}

}

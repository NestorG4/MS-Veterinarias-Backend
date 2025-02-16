package com.mx.clientes.service;

import java.util.List;
import java.util.Optional;

import com.mx.clientes.domain.Clientes;

public interface IClientesService {
	
	public List<Clientes> listar(); 
	
	public Clientes guardarClientes(Clientes clientes);
	
	public Optional<Clientes> buscarClientes(Long idCliente); 
	
	public void eliminarClientes(Long idCliente);
}

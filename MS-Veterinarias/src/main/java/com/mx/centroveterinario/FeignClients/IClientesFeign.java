package com.mx.centroveterinario.FeignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mx.centroveterinario.models.Clientes;

@FeignClient(name = "MS-Clientes", url = "http://localhost:8002", path = "/Clientes")
public interface IClientesFeign {
	@PostMapping(path = "guardar")
	public Clientes guardar(@RequestBody Clientes clientes);
	
	@PostMapping("/veterinaria/{veterinariaId}")
	public List	<Clientes> obtenerClientesPorVeterinariaId(@PathVariable Long veterinariaId); 

	
}

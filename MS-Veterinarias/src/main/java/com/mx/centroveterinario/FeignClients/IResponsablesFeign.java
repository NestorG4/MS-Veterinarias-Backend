package com.mx.centroveterinario.FeignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mx.centroveterinario.models.Responsables;

@FeignClient(name = "MS-Responsables", url = "http://localhost:8003", path = "/Responsables")
public interface IResponsablesFeign {
	
	@PostMapping(path = "guardar")
	public Responsables guardar(@RequestBody Responsables responsable);
	
	@PostMapping("/veterinaria/{veterinariaId}")
	public List	<Responsables> obtenerResponsablesPorVeterinariaId(@PathVariable Long veterinariaId); 

}

package com.mx.centroveterinario.FeignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mx.centroveterinario.models.Mascotas;

@FeignClient(name = "MS-Mascotas", url = "http://localhost:8004", path = "/Mascotas")
public interface IMascotasFeign {
	@PostMapping(path = "guardar")
	public Mascotas guardar(@RequestBody Mascotas mascotas);
	
	@PostMapping("/veterinaria/{veterinariaId}")
	public List	<Mascotas> obtenerMascotasPorVeterinariaId(@PathVariable Long veterinariaId); 

	
}

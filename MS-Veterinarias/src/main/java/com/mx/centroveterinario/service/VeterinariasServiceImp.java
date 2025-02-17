package com.mx.centroveterinario.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mx.centroveterinario.FeignClients.IClientesFeign;
import com.mx.centroveterinario.FeignClients.IMascotasFeign;
import com.mx.centroveterinario.FeignClients.IResponsablesFeign;
import org.springframework.web.client.RestTemplate;
import com.mx.centroveterinario.domain.Veterinarias;
import com.mx.centroveterinario.models.Clientes;
import com.mx.centroveterinario.models.Mascotas;
import com.mx.centroveterinario.models.Responsables;
import com.mx.centroveterinario.repository.IVeterinariaRepository;

@SuppressWarnings("unchecked")
@Service
public class VeterinariasServiceImp implements VeterinariaService {
	
	@Autowired
	private IVeterinariaRepository repository; 
	
	@Autowired
	private IResponsablesFeign  responsablesFC;
	
	@Autowired
	private IMascotasFeign mascotasFC; 
	
	@Autowired
	private IClientesFeign clientesFC;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<Veterinarias> veterinarias() {
		return repository.findAll(Sort.by(Direction.ASC, "idVeterinaria")); 
	}

	@Override
	public Veterinarias guardarVeterinaria(Veterinarias veterinarias) {
		return repository.save(veterinarias); 
	}

	@Override
	public Optional<Veterinarias> buscarVeterinaria(Long idVeterinaria) {
		return repository.findById(idVeterinaria); 
		
	}

	@Override
	public void eliminarVeterinaria(Long idVeterinaria) {
		Veterinarias veterinaria = buscarVeterinaria(idVeterinaria)
				.orElseThrow(()-> new RuntimeException("Veterinaria no encontrada: " + idVeterinaria));
		repository.delete(veterinaria);
	}
	
	//Metodos de FeignClient para Responsables
	
	public Responsables guardarResponsable(Responsables responsables) {
		return responsablesFC.guardar(responsables); 
	}
	
	public List<Responsables> obtenerResponsables(Long veterinariaId){
		return responsablesFC.obtenerResponsablesPorVeterinariaId(veterinariaId); 
	}
	
	//Metodos de RestTemplate para Clientes
	public Clientes saveClientes(Clientes clientes) {
		ResponseEntity<Clientes> cliente =  restTemplate.postForEntity("http://localhost:8002/Clientes/guardar", clientes, Clientes.class);
		return cliente.getBody(); 
	}

	public List<Clientes> getClientes(Long veterinariaId){
		return restTemplate.postForObject("http://localhost:8002/Clientes/veterinaria/" + veterinariaId, null,  List.class);
	}
	
	//Metodos de FeignClient para clientes
	public Clientes guardarClientes(Clientes clientes) {
		return clientesFC.guardar(clientes); 
	}
	
	public List<Responsables> obtenerClientes(Long veterinariaId){
		return responsablesFC.obtenerResponsablesPorVeterinariaId(veterinariaId); 
	}
	
	//Metodos de FeignClient para Mascotas
	public Mascotas guardarMascotas(Mascotas mascotas) {
		return mascotasFC.guardar(mascotas); 
	}
	
	public List<Mascotas> obtenerMascotas(Long veterinariaId){
		return mascotasFC.obtenerMascotasPorVeterinariaId(veterinariaId); 
	}
	
	//Obtener todos los modulos de Veterinaria
	
	public Map<String, Object> datosVeterinaria(Long veterinariaId){
		Map<String, Object> response = new LinkedHashMap<>();
		
		Optional<Veterinarias> veterinaria = repository.findById(veterinariaId);
		
		if(veterinaria.isEmpty()) {
			response.put("Mensaje: ", "La tienda con ID: " + veterinariaId + "no existe"); 
			return response; 
		}
		response.put("Veterinaria", veterinaria.get());
		
		List<Responsables> responsables = responsablesFC.obtenerResponsablesPorVeterinariaId(veterinariaId); 
		if (responsables.isEmpty()) {
			response.put("Responsables", "La veterinaria no tiene responsables"); 
		} else {
			response.put("Responsables", responsables);
		}
		
		List<Clientes> clientes = clientesFC.obtenerClientesPorVeterinariaId(veterinariaId); 
		if (clientes.isEmpty()) {
			response.put("Clientes", "La veterinaria no tiene clientes"); 
		} else {
			response.put("Clientes", clientes);
		}
		
		List<Mascotas> mascotas = mascotasFC.obtenerMascotasPorVeterinariaId(veterinariaId);
		if (mascotas.isEmpty()) {
			response.put("Mascotas", "La veterinaria no tiene mascotas"); 
		} else {
			response.put("Mascotas", mascotas);
		}
		
		return response; 
		
		 
		
	}
}

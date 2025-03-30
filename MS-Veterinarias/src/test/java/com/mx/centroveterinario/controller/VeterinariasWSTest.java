package com.mx.centroveterinario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mx.centroveterinario.domain.Veterinarias;
import com.mx.centroveterinario.service.VeterinariasServiceImp;

public class VeterinariasWSTest {
	//Inyecta los mocks para la clase que se esta probando
	@InjectMocks 
	private VeterinariasWS veterinariaWS; 
	
	//Creacion de objeto simulado
	@Mock
	private VeterinariasServiceImp service; 
	
	private Optional<Veterinarias> veterinaria3;
	private Veterinarias veterinaria1;
	private Veterinarias veterinaria2;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		veterinaria1 = new Veterinarias((long) 1, "Pow Patrols", "Av principal sn", (long)12140277);
		veterinaria2 = new Veterinarias((long) 2, "Patitas", "Av 7 centro sur", (long)232323233);
		veterinaria3 = Optional.ofNullable(new Veterinarias((long) 2, "Patitas", "Av 7 centro sur", (long)232323233));
	}
	
	
	@Test
	void testListar() {
		List<Veterinarias> veterinarias = Arrays.asList(veterinaria1, veterinaria2); 
		when(service.veterinarias()).thenReturn(veterinarias);
		ResponseEntity<?> response = veterinariaWS.listar();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(veterinarias, response.getBody());
	}
	
	@Test
	void testGuardar() {
		when(service.guardarVeterinaria(any(Veterinarias.class))).thenReturn(veterinaria1); 
		ResponseEntity<?> response = veterinariaWS.guardarVeterinaria(veterinaria1);
		assertEquals(HttpStatus.OK, response.getStatusCode()); 
		assertEquals(veterinaria1, response.getBody());
	}
	
	@Test
	void testBuscarExistente() {
		when(service.buscarVeterinaria((long) 2)).thenReturn(veterinaria3);
		ResponseEntity<?> response = veterinariaWS.buscarVeterinaria((long) 2);
		//assertEquals(HttpStatus.OK, response.getStatusCodeValue());
		assertEquals(veterinaria3, response.getBody());
	}
	
	@Test
	void testBuscarNoExistente() {
		when(service.buscarVeterinaria((long)4)).thenReturn(veterinaria3); 
		ResponseEntity<?> response = veterinariaWS.buscarVeterinaria((long) 4);
		//assertEquals(HttpStatus.NOT_FOUND, response.getStatusCodeValue());
		assertEquals(veterinaria3, response.getBody());
	}
}

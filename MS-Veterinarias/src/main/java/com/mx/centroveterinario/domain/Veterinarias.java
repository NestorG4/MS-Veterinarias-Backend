package com.mx.centroveterinario.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Veterinarias {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVeterinaria;
	private String nombre; 
	private String direccion; 
	private Long telefono; 
	
}

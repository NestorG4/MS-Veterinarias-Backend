package com.mx.mascotas.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Mascotas")
public class Mascotas {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMascota; 
	private String nombre; 
	private String raza; 
	private Long edad; 
	private String razonCita;
	private Long clienteId;
	private Long responsableId;
	private Long veterinariaId; 
}

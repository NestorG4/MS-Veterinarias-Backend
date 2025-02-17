package com.mx.centroveterinario.models;

import lombok.Data;

@Data
public class Mascotas {
	private Long idMascota; 
	private String nombre; 
	private String raza; 
	private Long edad; 
	private String razonCita;
	private Long clienteId;
	private Long responsableId;
	private Long veterinariaId;

}

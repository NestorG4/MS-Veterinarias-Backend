package com.mx.centroveterinario.models;

import lombok.Data;

@Data
public class Clientes {

	private Long idCliente;
	private String nombre;
	private String direccion;
	private Long contacto;
	private Long veterinariaId;
}

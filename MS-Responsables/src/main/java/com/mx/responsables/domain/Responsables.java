package com.mx.responsables.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "RESPONSABLES")
public class Responsables {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESPONSABLE")
    @SequenceGenerator(name = "SEQ_RESPONSABLE", sequenceName = "SEQ_RESPONSABLE", allocationSize = 1)
	private Long idResponsable; 
	private String nombre; 
	private Long contacto; 
	private Long veterinariaId;
}

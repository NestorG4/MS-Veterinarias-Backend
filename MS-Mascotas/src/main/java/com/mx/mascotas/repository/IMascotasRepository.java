package com.mx.mascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.mascotas.domain.Mascotas;

public interface IMascotasRepository extends JpaRepository<Mascotas, Long>{

}

package com.mx.centroveterinario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.centroveterinario.domain.Veterinarias;

public interface IVeterinariaRepository extends JpaRepository<Veterinarias, Long>{

}

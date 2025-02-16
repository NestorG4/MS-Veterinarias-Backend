package com.mx.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.clientes.domain.Clientes;

public interface IClientesRepository extends JpaRepository<Clientes, Long> {

}

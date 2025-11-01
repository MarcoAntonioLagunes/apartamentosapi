package com.demo.apartamentosapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.apartamentosapi.models.ClienteModel;

@Repository
public interface IClienteRepository extends JpaRepository<ClienteModel, Long> {

    // Buscar cliente por email
    Optional<ClienteModel> findByEmail(String email);

    // Buscar cliente por INE
    Optional<ClienteModel> findByIne(String ine);
}

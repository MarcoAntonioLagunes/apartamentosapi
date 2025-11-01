package com.bydaffi.apartamentosapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bydaffi.apartamentosapi.models.PropiedadModel;

@Repository
public interface IPropiedadRepository extends JpaRepository<PropiedadModel, Long> {

    // Query methods de Spring Data JPA
    List<PropiedadModel> findByTipo(PropiedadModel.TipoPropiedad tipo);

    List<PropiedadModel> findByPropietarioId(Long propietarioId);
}

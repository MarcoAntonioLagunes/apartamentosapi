package com.bydaffi.apartamentosapi.repositories;

import com.bydaffi.apartamentosapi.models.PropiedadImagenesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPropiedadImagenesRepository extends JpaRepository<PropiedadImagenesModel, Integer> {

    // Find all images for a specific property
    List<PropiedadImagenesModel> findByPropiedadIdPropiedad(Long propiedadId);

    // Find all images for a property ordered by orden field
    List<PropiedadImagenesModel> findByPropiedadIdPropiedadOrderByOrdenAsc(Long propiedadId);

    // Find the principal image for a property
    Optional<PropiedadImagenesModel> findByPropiedadIdPropiedadAndEsPrincipal(Long propiedadId, Boolean esPrincipal);
}

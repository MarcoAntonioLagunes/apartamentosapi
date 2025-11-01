package com.demo.apartamentosapi.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.apartamentosapi.models.DisponibilidadModel;

@Repository
public interface IDisponibilidadRepository extends JpaRepository<DisponibilidadModel, Integer> {

    // Find all disponibilidades for a specific property
    List<DisponibilidadModel> findByPropiedadIdPropiedad(Long propiedadId);

    // Find disponibilidades by date range
    List<DisponibilidadModel> findByFechaBetween(LocalDate start, LocalDate end);

    // Find disponibilidades for a property with specific availability status
    List<DisponibilidadModel> findByPropiedadIdPropiedadAndDisponible(Long propiedadId, Boolean disponible);

    // Find disponibilidad for a specific property on a specific date
    @Query("SELECT d FROM DisponibilidadModel d WHERE d.propiedad.id_propiedad = :propiedadId AND d.fecha = :fecha")
    List<DisponibilidadModel> findByPropiedadAndFecha(@Param("propiedadId") Long propiedadId, @Param("fecha") LocalDate fecha);
}

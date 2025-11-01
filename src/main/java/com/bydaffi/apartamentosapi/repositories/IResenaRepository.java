package com.bydaffi.apartamentosapi.repositories;

import com.bydaffi.apartamentosapi.models.ResenaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResenaRepository extends JpaRepository<ResenaModel, Integer> {

    // Find reseña by reservacion
    List<ResenaModel> findByReservacionId(Integer reservacionId);

    // Find all reseñas for a specific property (through reservacion)
    @Query("SELECT r FROM ResenaModel r WHERE r.reservacion.propiedad.id_propiedad = :propiedadId")
    List<ResenaModel> findByPropiedadId(@Param("propiedadId") Long propiedadId);

    // Find all reseñas made by a specific client (through reservacion)
    @Query("SELECT r FROM ResenaModel r WHERE r.reservacion.cliente.id_cliente = :clienteId")
    List<ResenaModel> findByClienteId(@Param("clienteId") Long clienteId);

    // Find reseñas with responses from property owner
    @Query("SELECT r FROM ResenaModel r WHERE r.respuestaPropietario IS NOT NULL")
    List<ResenaModel> findResenasWithRespuestaPropietario();
}

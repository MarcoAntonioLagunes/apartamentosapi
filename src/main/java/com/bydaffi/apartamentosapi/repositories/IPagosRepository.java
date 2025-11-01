package com.bydaffi.apartamentosapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bydaffi.apartamentosapi.models.PagosModel;

@Repository
public interface IPagosRepository extends JpaRepository<PagosModel, Long> {

    // Query methods de Spring Data JPA
    @Query("SELECT p FROM PagosModel p WHERE p.reserva.id_reserva = :reservaId")
    List<PagosModel> findByReservaId(@Param("reservaId") Long reservaId);

    @Query("SELECT p FROM PagosModel p WHERE p.estado_pago = :estadoPago")
    List<PagosModel> findByEstadoPago(@Param("estadoPago") PagosModel.EstadoPago estadoPago);

    @Query("SELECT p FROM PagosModel p WHERE p.metodo_pago = :metodoPago")
    List<PagosModel> findByMetodoPago(@Param("metodoPago") PagosModel.MetodoPago metodoPago);

    @Query("SELECT p FROM PagosModel p WHERE p.reserva.id_reserva = :reservaId AND p.estado_pago = :estadoPago")
    List<PagosModel> findByReservaIdAndEstadoPago(@Param("reservaId") Long reservaId, @Param("estadoPago") PagosModel.EstadoPago estadoPago);
}

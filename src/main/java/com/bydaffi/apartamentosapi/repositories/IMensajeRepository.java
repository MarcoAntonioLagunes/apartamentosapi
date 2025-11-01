package com.bydaffi.apartamentosapi.repositories;

import com.bydaffi.apartamentosapi.models.MensajeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMensajeRepository extends JpaRepository<MensajeModel, Integer> {

    // Find all messages sent by a specific client
    List<MensajeModel> findByRemitenteId(Long remitenteId);

    // Find all messages received by a specific client
    List<MensajeModel> findByDestinatarioId(Long destinatarioId);

    // Find all unread messages for a specific recipient
    List<MensajeModel> findByDestinatarioIdAndLeido(Long destinatarioId, Boolean leido);

    // Find all messages related to a specific reservation
    List<MensajeModel> findByReservacionId(Integer reservacionId);

    // Find all messages between two clients (both directions) - ordered by date
    @Query("SELECT m FROM MensajeModel m WHERE " +
           "(m.remitente.id = :clienteId1 AND m.destinatario.id = :clienteId2) OR " +
           "(m.remitente.id = :clienteId2 AND m.destinatario.id = :clienteId1) " +
           "ORDER BY m.fechaEnvio ASC")
    List<MensajeModel> findConversacionBetweenClientes(
            @Param("clienteId1") Long clienteId1,
            @Param("clienteId2") Long clienteId2
    );
}

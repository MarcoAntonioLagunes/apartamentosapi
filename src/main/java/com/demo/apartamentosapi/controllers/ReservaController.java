package com.demo.apartamentosapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.apartamentosapi.models.ReservaModel;
import com.demo.apartamentosapi.services.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Obtener todas las reservas
    @GetMapping
    public List<ReservaModel> getAllReservas() {
        return reservaService.getAllReservas();
    }

    // Obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservaModel> getReservaById(@PathVariable Long id) {
        Optional<ReservaModel> reserva = reservaService.getReservaById(id);
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener reservas por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<ReservaModel> getReservasByCliente(@PathVariable Long clienteId) {
        return reservaService.getReservasByCliente(clienteId);
    }

    // Obtener reservas por propiedad
    @GetMapping("/propiedad/{propiedadId}")
    public List<ReservaModel> getReservasByPropiedad(@PathVariable Long propiedadId) {
        return reservaService.getReservasByPropiedad(propiedadId);
    }

    // Obtener reservas por estado
    @GetMapping("/estado/{estado}")
    public List<ReservaModel> getReservasByEstado(@PathVariable ReservaModel.EstadoReserva estado) {
        return reservaService.getReservasByEstado(estado);
    }

    // Obtener reservas por cliente y estado
    @GetMapping("/cliente/{clienteId}/estado")
    public List<ReservaModel> getReservasByClienteAndEstado(
            @PathVariable Long clienteId,
            @RequestParam ReservaModel.EstadoReserva estado) {
        return reservaService.getReservasByClienteAndEstado(clienteId, estado);
    }

    // Crear nueva reserva
    @PostMapping
    public ReservaModel createReserva(@RequestBody ReservaModel reserva) {
        return reservaService.saveReserva(reserva);
    }

    // Actualizar reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<ReservaModel> updateReserva(
            @PathVariable Long id,
            @RequestBody ReservaModel reservaDetails) {
        return reservaService.updateReserva(id, reservaDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        Optional<ReservaModel> reserva = reservaService.getReservaById(id);
        if (reserva.isPresent()) {
            reservaService.deleteReserva(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

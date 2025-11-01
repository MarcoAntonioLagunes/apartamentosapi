package com.demo.apartamentosapi.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.demo.apartamentosapi.models.DisponibilidadModel;
import com.demo.apartamentosapi.services.DisponibilidadService;

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    // Obtener todas las disponibilidades
    @GetMapping
    public List<DisponibilidadModel> getAllDisponibilidades() {
        return disponibilidadService.getAllDisponibilidades();
    }

    // Obtener una disponibilidad por ID
    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> getDisponibilidadById(@PathVariable Integer id) {
        Optional<DisponibilidadModel> disponibilidad = disponibilidadService.getDisponibilidadById(id);
        return disponibilidad.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener disponibilidades por propiedad
    @GetMapping("/propiedad/{propiedadId}")
    public List<DisponibilidadModel> getDisponibilidadesByPropiedad(@PathVariable Long propiedadId) {
        return disponibilidadService.getDisponibilidadesByPropiedad(propiedadId);
    }

    // Obtener disponibilidades por propiedad y estado de disponibilidad
    @GetMapping("/propiedad/{propiedadId}/disponible")
    public List<DisponibilidadModel> getDisponibilidadesByPropiedadAndDisponible(
            @PathVariable Long propiedadId,
            @RequestParam(defaultValue = "true") Boolean disponible) {
        return disponibilidadService.getDisponibilidadesByPropiedadAndDisponible(propiedadId, disponible);
    }

    // Obtener disponibilidades por rango de fechas
    @GetMapping("/fechas")
    public List<DisponibilidadModel> getDisponibilidadesByFechaRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return disponibilidadService.getDisponibilidadesByFechaRange(start, end);
    }

    // Obtener disponibilidades por propiedad y fecha específica
    @GetMapping("/propiedad/{propiedadId}/fecha")
    public List<DisponibilidadModel> getDisponibilidadesByPropiedadAndFecha(
            @PathVariable Long propiedadId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return disponibilidadService.getDisponibilidadesByPropiedadAndFecha(propiedadId, fecha);
    }

    // Crear nueva disponibilidad
    @PostMapping
    public DisponibilidadModel createDisponibilidad(@RequestBody DisponibilidadModel disponibilidad) {
        return disponibilidadService.saveDisponibilidad(disponibilidad);
    }

    // Actualizar disponibilidad existente
    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> updateDisponibilidad(
            @PathVariable Integer id,
            @RequestBody DisponibilidadModel disponibilidadDetails) {
        return disponibilidadService.updateDisponibilidad(id, disponibilidadDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una disponibilidad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisponibilidad(@PathVariable Integer id) {
        Optional<DisponibilidadModel> disponibilidad = disponibilidadService.getDisponibilidadById(id);
        if (disponibilidad.isPresent()) {
            disponibilidadService.deleteDisponibilidad(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.bydaffi.apartamentosapi.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.bydaffi.apartamentosapi.models.MensajeModel;
import com.bydaffi.apartamentosapi.services.MensajeService;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    // Obtener todos los mensajes
    @GetMapping
    public List<MensajeModel> getAllMensajes() {
        return mensajeService.getAllMensajes();
    }

    // Obtener un mensaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<MensajeModel> getMensajeById(@PathVariable Integer id) {
        Optional<MensajeModel> mensaje = mensajeService.getMensajeById(id);
        return mensaje.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener mensajes enviados por un cliente
    @GetMapping("/enviados/{clienteId}")
    public List<MensajeModel> getMensajesEnviados(@PathVariable Long clienteId) {
        return mensajeService.getMensajesEnviados(clienteId);
    }

    // Obtener mensajes recibidos por un cliente
    @GetMapping("/recibidos/{clienteId}")
    public List<MensajeModel> getMensajesRecibidos(@PathVariable Long clienteId) {
        return mensajeService.getMensajesRecibidos(clienteId);
    }

    // Obtener mensajes no leídos de un cliente
    @GetMapping("/no-leidos/{clienteId}")
    public List<MensajeModel> getMensajesNoLeidos(@PathVariable Long clienteId) {
        return mensajeService.getMensajesNoLeidos(clienteId);
    }

    // Obtener conversación entre dos clientes
    @GetMapping("/conversacion/{clienteId1}/{clienteId2}")
    public List<MensajeModel> getConversacion(
            @PathVariable Long clienteId1,
            @PathVariable Long clienteId2) {
        return mensajeService.getConversacion(clienteId1, clienteId2);
    }

    // Obtener mensajes relacionados con una reservación
    @GetMapping("/reservacion/{reservacionId}")
    public List<MensajeModel> getMensajesByReservacion(@PathVariable Integer reservacionId) {
        return mensajeService.getMensajesByReservacion(reservacionId);
    }

    // Crear/enviar nuevo mensaje
    @PostMapping
    public MensajeModel createMensaje(@RequestBody MensajeModel mensaje) {
        return mensajeService.saveMensaje(mensaje);
    }

    // Actualizar mensaje existente
    @PutMapping("/{id}")
    public ResponseEntity<MensajeModel> updateMensaje(
            @PathVariable Integer id,
            @RequestBody MensajeModel mensajeDetails) {
        return mensajeService.updateMensaje(id, mensajeDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Marcar mensaje como leído
    @PutMapping("/{id}/marcar-leido")
    public ResponseEntity<MensajeModel> marcarComoLeido(@PathVariable Integer id) {
        return mensajeService.marcarComoLeido(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Marcar todos los mensajes de un cliente como leídos
    @PutMapping("/marcar-todos-leidos/{clienteId}")
    public ResponseEntity<Void> marcarTodosComoLeidos(@PathVariable Long clienteId) {
        mensajeService.marcarTodosComoLeidos(clienteId);
        return ResponseEntity.ok().build();
    }

    // Eliminar un mensaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Integer id) {
        Optional<MensajeModel> mensaje = mensajeService.getMensajeById(id);
        if (mensaje.isPresent()) {
            mensajeService.deleteMensaje(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

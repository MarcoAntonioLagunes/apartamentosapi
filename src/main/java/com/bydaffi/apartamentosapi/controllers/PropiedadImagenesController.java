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

import com.bydaffi.apartamentosapi.models.PropiedadImagenesModel;
import com.bydaffi.apartamentosapi.services.PropiedadImagenesService;

@RestController
@RequestMapping("/api/propiedad-imagenes")
public class PropiedadImagenesController {

    @Autowired
    private PropiedadImagenesService propiedadImagenesService;

    // Obtener todas las imágenes
    @GetMapping
    public List<PropiedadImagenesModel> getAllImagenes() {
        return propiedadImagenesService.getAllImagenes();
    }

    // Obtener una imagen por ID
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> getImagenById(@PathVariable Integer id) {
        Optional<PropiedadImagenesModel> imagen = propiedadImagenesService.getImagenById(id);
        return imagen.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener todas las imágenes de una propiedad (ordenadas)
    @GetMapping("/propiedad/{propiedadId}")
    public List<PropiedadImagenesModel> getImagenesByPropiedad(@PathVariable Long propiedadId) {
        return propiedadImagenesService.getImagenesByPropiedad(propiedadId);
    }

    // Obtener la imagen principal de una propiedad
    @GetMapping("/propiedad/{propiedadId}/principal")
    public ResponseEntity<PropiedadImagenesModel> getImagenPrincipal(@PathVariable Long propiedadId) {
        Optional<PropiedadImagenesModel> imagen = propiedadImagenesService.getImagenPrincipalByPropiedad(propiedadId);
        return imagen.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva imagen
    @PostMapping
    public PropiedadImagenesModel createImagen(@RequestBody PropiedadImagenesModel imagen) {
        return propiedadImagenesService.saveImagen(imagen);
    }

    // Actualizar imagen existente
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> updateImagen(
            @PathVariable Integer id,
            @RequestBody PropiedadImagenesModel imagenDetails) {
        return propiedadImagenesService.updateImagen(id, imagenDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una imagen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Integer id) {
        Optional<PropiedadImagenesModel> imagen = propiedadImagenesService.getImagenById(id);
        if (imagen.isPresent()) {
            propiedadImagenesService.deleteImagen(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

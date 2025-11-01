package com.bydaffi.apartamentosapi.controllers;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bydaffi.apartamentosapi.models.PropiedadModel;
import com.bydaffi.apartamentosapi.services.PropiedadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    // Aquí irán los métodos para manejar las solicitudes HTTP relacionadas con las propiedades
    // Obtener todas las propiedades, obtener por ID, crear, actualizar, eliminar, etc.

    //Recuperar todas la propiedades
    @GetMapping
    public List<PropiedadModel> getAllPropiedades() {
        return propiedadService.getAllPropiedades();
    }

    //Propiedad por ID
    //... Similar a ClienteController
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadModel> getPropiedadesById(@PathVariable Long id) {
        Optional<PropiedadModel> propiedad = propiedadService.getPropiedadById(id);
        return propiedad.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva propiedad
    @PostMapping
    public PropiedadModel createPropiedad(@Valid @RequestBody PropiedadModel propiedad) {
        return propiedadService.savePropiedad(propiedad);
    }

    // Actualizar propiedad existente
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadModel> updatePropiedad(@PathVariable Long id, @Valid @RequestBody PropiedadModel propiedadDetails) {
        return propiedadService.updatePropiedad(id, propiedadDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una propiedad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropiedad(@PathVariable Long id) {
        Optional<PropiedadModel> propiedad = propiedadService.getPropiedadById(id);
        if (propiedad.isPresent()) {
            propiedadService.deletePropiedad(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Recuperar propiedades por tipo
    @GetMapping("/tipo/{tipo}")
    public List<PropiedadModel> getPropiedadesByTipo(@PathVariable PropiedadModel.TipoPropiedad tipo) {
        return propiedadService.getPropiedadesByTipo(tipo);
    }

    // Recuperar todas las propiedades de un Propietario
    @GetMapping("/propietario/{propietarioId}")
    public List<PropiedadModel> getPropiedadesByPropietario(@PathVariable Long propietarioId) {
        return propiedadService.getPropiedadesByPropietario(propietarioId);
    }
}

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
import com.demo.apartamentosapi.models.PagosModel;
import com.demo.apartamentosapi.services.PagosService;

@RestController
@RequestMapping("/api/pagos")
public class PagosController {
    @Autowired
    private PagosService pagosService;

    @GetMapping
    public List<PagosModel> getAllPagos() {
        return pagosService.getAllPagos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagosModel> getPagoById(@PathVariable Long id) {
        Optional<PagosModel> pago = pagosService.getPagoById(id);
        return pago.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reserva/{reservaId}")
    public List<PagosModel> getPagosByReserva(@PathVariable Long reservaId) {
        return pagosService.getPagosByReserva(reservaId);
    }

    @GetMapping("/estado/{estadoPago}")
    public List<PagosModel> getPagosByEstado(@PathVariable PagosModel.EstadoPago estadoPago) {
        return pagosService.getPagosByEstado(estadoPago);
    }

    @GetMapping("/metodo/{metodoPago}")
    public List<PagosModel> getPagosByMetodoPago(@PathVariable PagosModel.MetodoPago metodoPago) {
        return pagosService.getPagosByMetodoPago(metodoPago);
    }

    @GetMapping("/reserva/{reservaId}/estado")
    public List<PagosModel> getPagosByReservaAndEstado(
            @PathVariable Long reservaId,
            @RequestParam PagosModel.EstadoPago estadoPago) {
        return pagosService.getPagosByReservaAndEstado(reservaId, estadoPago);
    }

    @PostMapping
    public PagosModel createPago(@RequestBody PagosModel pago) {
        return pagosService.savePago(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagosModel> updatePago(
            @PathVariable Long id,
            @RequestBody PagosModel pagoDetails) {
        return pagosService.updatePago(id, pagoDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        Optional<PagosModel> pago = pagosService.getPagoById(id);
        if (pago.isPresent()) {
            pagosService.deletePago(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

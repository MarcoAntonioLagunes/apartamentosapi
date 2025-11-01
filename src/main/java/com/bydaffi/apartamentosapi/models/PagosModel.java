package com.bydaffi.apartamentosapi.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private ReservaModel reserva;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodo_pago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estado_pago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fecha_pago;

    @Column(name = "referencia_pago", nullable = false, length = 100)
    private String referenciaPago;

    @Column(name = "detalles_pago", length = 500)
    private String detalles_pago;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_at;

    public enum MetodoPago {
        TARJETA_CREDITO,
        TARJETA_DEBITO,
        EFECTIVO,
        TRANSFERENCIA
    }

    public enum EstadoPago {
        PENDIENTE,
        COMPLETADO,
        FALLIDO,
        REEMBOLSADO
    }

    @PrePersist
    public void prePersist() {
        if (this.created_at == null) {
            this.created_at = LocalDateTime.now();
        }
        if (this.fecha_pago == null) {
            this.fecha_pago = LocalDate.now();
        }
        if (this.estado_pago == null) {
            this.estado_pago = EstadoPago.PENDIENTE;
        }
    }
}

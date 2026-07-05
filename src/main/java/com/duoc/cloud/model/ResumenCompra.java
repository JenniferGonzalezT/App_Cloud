package com.duoc.cloud.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "resumen_compras")
@Data
public class ResumenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resumen_seq")
    @SequenceGenerator(name = "resumen_seq", sequenceName = "RESUMEN_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "inscripcion_id_original")
    private Long inscripcionIdOriginal;

    @Column(name = "nombre_estudiante")
    private String nombreEstudiante;

    @Column(name = "total_pagar")
    private BigDecimal totalPagar;

    @Column(name = "fecha_procesamiento")
    private LocalDateTime fechaProcesamiento;
}

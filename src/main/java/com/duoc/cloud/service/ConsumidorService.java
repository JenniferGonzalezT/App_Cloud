package com.duoc.cloud.service;

import com.duoc.cloud.config.RabbitMQConfig;
import com.duoc.cloud.dto.InscripcionResumenDTO;
import com.duoc.cloud.model.ResumenCompra;
import com.duoc.cloud.repository.ResumenCompraRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsumidorService {

    private final ResumenCompraRepository resumenCompraRepository;

    public ConsumidorService(ResumenCompraRepository resumenCompraRepository) {
        this.resumenCompraRepository = resumenCompraRepository;
    }

    /**
     * Este método escucha la cola de RabbitMQ de manera asíncrona.
     * @RabbitListener se encarga de interceptar el JSON, deserializarlo 
     * y convertirlo al objeto DTO.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumirMensajeInscripcion(InscripcionResumenDTO resumen) {
        System.out.println("====== CONSUMER: ¡Mensaje recibido desde RabbitMQ! ======");
        System.out.println("Procesando asíncronamente la inscripción del estudiante: " + resumen.getNombreEstudiante());

        try {
            // Mapeamos los datos del DTO recibido a nuestra Entidad de Base de Datos
            ResumenCompra compra = new ResumenCompra();
            compra.setInscripcionIdOriginal(resumen.getInscripcionId());
            compra.setNombreEstudiante(resumen.getNombreEstudiante());
            compra.setTotalPagar(resumen.getTotalPagar());
            compra.setFechaProcesamiento(LocalDateTime.now()); // Marca de tiempo Cloud

            // Guardamos en la nueva tabla resumen_compras de Oracle Cloud
            resumenCompraRepository.save(compra);
            
            System.out.println("====== CONSUMER: Registro guardado exitosamente en Oracle Cloud ======");
        } catch (Exception e) {
            System.err.println("ERROR EN EL CONSUMIDOR al persistir en Oracle: " + e.getMessage());
        }
    }
}

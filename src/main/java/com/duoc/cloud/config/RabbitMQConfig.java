package com.duoc.cloud.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nombres constantes para usar en toda la app
    public static final String QUEUE_NAME = "inscripcionQueue";
    public static final String EXCHANGE_NAME = "inscripcionExchange";
    public static final String ROUTING_KEY = "inscripcionRoutingKey";

    // 1. Creación de la Cola
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // true = la cola sobrevive si RabbitMQ se reinicia
    }

    // 2. Creación del Exchange tipo Direct
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 3. Enlazar la cola con el Exchange usando la Routing Key
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // 4. Conversor para enviar objetos Java como JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

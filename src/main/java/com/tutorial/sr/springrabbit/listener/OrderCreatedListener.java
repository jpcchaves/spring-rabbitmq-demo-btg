package com.tutorial.sr.springrabbit.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.sr.springrabbit.dto.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.tutorial.sr.springrabbit.config.rabbitmq.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    public OrderCreatedListener(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        this.jackson2JsonMessageConverter = jackson2JsonMessageConverter;
    }

    @RabbitListener(queues = {ORDER_CREATED_QUEUE})
    public void listen(Message<OrderCreatedEvent> message) {

        logger.info("Message consumed {}", message.getPayload());

    }
}

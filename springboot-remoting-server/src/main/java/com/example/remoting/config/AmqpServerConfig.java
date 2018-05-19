package com.example.remoting.config;

import com.example.remoting.service.DnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AmqpServerConfig {

    @Bean
    Queue rabbitmqQueue() {
        return new Queue("dnsQueryServiceQueue");
    }

    @Autowired
    @Qualifier(value = "DnsServiceAmqpImpl")
    DnsService dnsService;

    @Autowired
    Queue queue;

    @Bean
    AmqpInvokerServiceExporter amqpInvokerServiceExporter(RabbitTemplate rabbitTemplate) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setServiceInterface(DnsService.class);
        exporter.setService(dnsService);
        exporter.setAmqpTemplate(rabbitTemplate);
        return exporter;
    }

    @Bean
    SimpleMessageListenerContainer amqpListenerContainer(
        ConnectionFactory factory,
        AmqpInvokerServiceExporter exporter
    ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.setMessageListener(exporter);
        container.setQueueNames(queue.getName());
        return container;
    }
}

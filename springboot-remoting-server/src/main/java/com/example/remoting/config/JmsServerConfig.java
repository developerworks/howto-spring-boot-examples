package com.example.remoting.config;

import com.example.remoting.service.DnsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@EnableJms
@Configuration
@Slf4j
public class JmsServerConfig {

//    @Bean
//    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }
//
//    @Bean
//    public MessageConverter jacksonJmsMessageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }

    public static final String DNS_QUERY_QUEUE = "dnsQueryServiceQueue";

    @Autowired
    @Qualifier(value = "DnsServiceJmsImpl")
    DnsService dnsService;

    @Bean
    Queue queue() {
        return new ActiveMQQueue(DNS_QUERY_QUEUE);
    }

    @Bean
    SimpleMessageListenerContainer jmsListenerContainer(ConnectionFactory connectionFactory, JmsInvokerServiceExporter exporter) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(DNS_QUERY_QUEUE);
        container.setConcurrentConsumers(10);
        container.setMessageListener(exporter);
        return container;
    }

    @Bean
    JmsInvokerServiceExporter jmsInvokerServiceExporter() {
        JmsInvokerServiceExporter exporter = new JmsInvokerServiceExporter();
        exporter.setService(dnsService);
        exporter.setServiceInterface(DnsService.class);
        return exporter;
    }

}


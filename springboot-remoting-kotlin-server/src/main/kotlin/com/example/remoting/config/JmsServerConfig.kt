package com.example.remoting.config

import com.example.remoting.service.DnsService
import org.apache.activemq.command.ActiveMQQueue
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.listener.SimpleMessageListenerContainer
import org.springframework.jms.remoting.JmsInvokerServiceExporter
import javax.jms.ConnectionFactory
import javax.jms.Queue

@EnableJms
@Configuration
class JmsServerConfig {
    companion object {
        val log = LoggerFactory.getLogger(JmsServerConfig::class.java.name)
        val DNS_QUERY_QUEUE = "dnsQueryServiceQueue1"
    }

    @Autowired
    @Qualifier(value = "DnsServiceJmsImpl")
    internal lateinit var dnsService: DnsService

    @Bean
    internal fun queue(): Queue {
        return ActiveMQQueue(DNS_QUERY_QUEUE)
    }

    @Bean
    internal fun jmsListenerContainer(connectionFactory: ConnectionFactory, exporter: JmsInvokerServiceExporter): SimpleMessageListenerContainer {

        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.destinationName = DNS_QUERY_QUEUE
        container.setConcurrentConsumers(10)
        container.messageListener = exporter
        return container
    }

    @Bean
    internal fun jmsInvokerServiceExporter(): JmsInvokerServiceExporter {
        val exporter = JmsInvokerServiceExporter()
        exporter.service = dnsService
        exporter.serviceInterface = DnsService::class.java
        return exporter
    }
}
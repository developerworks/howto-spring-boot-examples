package com.example.remoting

import org.apache.activemq.command.ActiveMQQueue
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean
import javax.jms.ConnectionFactory

@Configuration
class BeanConfig {

    private val log = LoggerFactory.getLogger(BeanConfig::class.java)

    @Bean
    fun jmsQueue(): ActiveMQQueue {
        log.info(">>> Init jmsQueue")
        return ActiveMQQueue("dnsQueryServiceQueue1")
    }

    @Bean
    fun jmsInvoker(jmsConnectionFactory: ConnectionFactory, jmsQueue: ActiveMQQueue): DnsService {
        log.info(">>> Init jmsInvoker")

        var factoryBean = JmsInvokerProxyFactoryBean()
        factoryBean.setServiceInterface(DnsService::class.java)
        factoryBean.setConnectionFactory(jmsConnectionFactory)
        factoryBean.setQueue(jmsQueue)

        factoryBean.afterPropertiesSet()
        return factoryBean.`object` as DnsService
    }

}
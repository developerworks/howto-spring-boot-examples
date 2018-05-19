package com.example.remoting;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class BeanConfig {

    /**
     * JMS Queue
     *
     * @return
     */
    @Bean(name = "jmsQueue")
    Queue jmsQueue() {
        return new ActiveMQQueue("dnsQueryServiceQueue");
    }

    /**
     * AMQP Queue
     *
     * @return
     */
    @Bean
    org.springframework.amqp.core.Queue amqpQueue() {
        return new org.springframework.amqp.core.Queue("dnsQueryServiceQueue");
    }

    @Autowired
    org.springframework.amqp.core.Queue amqpQueue;

    @Bean
    Exchange directExchange() {
        DirectExchange exchange = new DirectExchange("remoting.exchange");
        BindingBuilder.bind(amqpQueue).to(exchange).with("remoting.dnsQueryServiceQueue");

        return exchange;
    }

    @Bean
    RabbitTemplate amqpTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange("remoting.exchange");
        template.setRoutingKey("remoting.dnsQueryServiceQueue");
        return template;
    }

    // =============================
    //  PROXY FACTORY BEANS

    // Before creating the proxy bean, the factory bean must be initialized,
    // which is why in the Java Configuration,the method afterPropertiesSet()
    // must be called explicitly.

    // Otherwise, client side of remoting will get the error:
    //          Caused by: org.springframework.remoting.RemoteProxyFailureException: No reply received from
    // =============================

    @Bean(name = "Rmi")
    DnsService rmi() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://127.0.0.1:1099/DnsService");
        factoryBean.setServiceInterface(DnsService.class);

        factoryBean.afterPropertiesSet();
        return (DnsService) factoryBean.getObject();
    }

    @Bean(name = "Hessian")
    DnsService hessian() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl("http://127.0.0.1:9090/HessianDnsService");
        factoryBean.setServiceInterface(DnsService.class);

        factoryBean.afterPropertiesSet();
        return (DnsService) factoryBean.getObject();
    }

    @Bean(name = "HttpInvoker")
    DnsService httpInvoker() {
        HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceInterface(DnsService.class);
        factoryBean.setServiceUrl("http://127.0.0.1:9090/HttpInvokerDnsService");

        factoryBean.afterPropertiesSet();
        return (DnsService) factoryBean.getObject();
    }

    @Bean(name = "JaxWs")
    DnsService jaxWs() throws MalformedURLException {
        JaxWsPortProxyFactoryBean factoryBean = new JaxWsPortProxyFactoryBean();
        factoryBean.setWsdlDocumentUrl(new URL("http://localhost:9091/jaxws/WsDnsService?wsdl"));
        factoryBean.setServiceName("WsDnsService");
        factoryBean.setPortName("DnsServiceWsImplPort");
        factoryBean.setServiceInterface(DnsService.class);
        factoryBean.setNamespaceUri("http://www.example.com/");

        factoryBean.afterPropertiesSet();
        return (DnsService) factoryBean.getObject();
    }

    @Bean(name = "jmsInvoker")
    DnsService jmsInvoker(ConnectionFactory connectionFactory, Queue queue) {
        JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
        factoryBean.setServiceInterface(DnsService.class);
        factoryBean.setConnectionFactory(connectionFactory);
        factoryBean.setQueue(queue);

        factoryBean.afterPropertiesSet();
        return (DnsService) factoryBean.getObject();
    }

    @Bean(name = "Amqp")
    DnsService amqpFactoryBean(RabbitTemplate rabbitTemplate) throws Exception {
        AmqpProxyFactoryBean factoryBean = new AmqpProxyFactoryBean();
        factoryBean.setServiceInterface(DnsService.class);
        factoryBean.setAmqpTemplate(rabbitTemplate);

        factoryBean.afterPropertiesSet();
        return (DnsService) factoryBean.getObject();
    }

}

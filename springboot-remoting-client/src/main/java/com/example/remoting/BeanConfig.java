package com.example.remoting;

import org.apache.activemq.command.ActiveMQQueue;
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

    @Bean
    Queue queue() {
        return new ActiveMQQueue("dnsQueryServiceQueue");
    }

    @Bean(name = "RmiDnsService")
    RmiProxyFactoryBean rmiDnsService() {
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://127.0.0.1:1099/DnsService");
        factoryBean.setServiceInterface(DnsService.class);
        return factoryBean;
    }

    @Bean(name = "HessianDnsService")
    HessianProxyFactoryBean hessianDnsService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl("http://127.0.0.1:9090/HessianDnsService");
        factoryBean.setServiceInterface(DnsService.class);
        return factoryBean;
    }

    @Bean(name = "HttpInvokerDnsService")
    HttpInvokerProxyFactoryBean httpInvokerDnsService() {
        HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceInterface(DnsService.class);
        factoryBean.setServiceUrl("http://127.0.0.1:9090/HttpInvokerDnsService");
        return factoryBean;
    }

    @Bean(name = "JaxWsDnsService")
    JaxWsPortProxyFactoryBean jaxWsDnsService() throws MalformedURLException {
        JaxWsPortProxyFactoryBean factoryBean = new JaxWsPortProxyFactoryBean();
        factoryBean.setWsdlDocumentUrl(new URL("http://localhost:9091/jaxws/WsDnsService?wsdl"));
        factoryBean.setServiceName("WsDnsService");
        factoryBean.setPortName("DnsServiceWsImplPort");
        factoryBean.setServiceInterface(DnsService.class);
        factoryBean.setNamespaceUri("http://www.example.com/");

        return factoryBean;
    }

    @Bean(name = "JmsDnsService")
    JmsInvokerProxyFactoryBean jmxInvokerDnsService(ConnectionFactory connectionFactory, Queue queue) {
        JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
        factoryBean.setServiceInterface(DnsService.class);
        factoryBean.setConnectionFactory(connectionFactory);
        factoryBean.setQueue(queue);
        return factoryBean;
    }

}

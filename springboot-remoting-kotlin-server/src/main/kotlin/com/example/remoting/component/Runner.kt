package com.example.remoting.component

import com.example.remoting.config.JmsServerConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.jms.core.JmsTemplate

class Runner : CommandLineRunner {
    companion object {
        val log: Logger = LoggerFactory.getLogger(Runner::class.java.name)
    }
    @Autowired
    private lateinit var jmsTemplate: JmsTemplate

    override fun run(vararg args: String?) {
        log.info("Send an email.")
        jmsTemplate.convertAndSend("dnsQueryServiceQueue1", Email("info@example.com", "Hello"))
    }
}
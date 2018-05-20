package com.example.remoting.service.impl

import com.example.remoting.service.DnsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
@Qualifier(value = "DnsServiceJmsImpl")
class DnsServiceJmsImpl : DnsService {
    private val log = LoggerFactory.getLogger(DnsServiceJmsImpl::class.java)


    @Autowired
    lateinit var businessLogic: BusinessLogic

    override fun getName(ip: String): String {
        var name: String = businessLogic.getName(ip)
        log.info("Jms remoting method called, return value: {}", name)

        return name
    }
}
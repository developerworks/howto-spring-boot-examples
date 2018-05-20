package com.example.remoting.service.impl

import org.springframework.stereotype.Service

@Service
class BusinessLogic {
    fun getName(ip: String): String {
        return "www.google.com"
    }
}
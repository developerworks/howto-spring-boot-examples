package com.example.remoting


interface DnsService {
    fun getName(ip: String): String
}
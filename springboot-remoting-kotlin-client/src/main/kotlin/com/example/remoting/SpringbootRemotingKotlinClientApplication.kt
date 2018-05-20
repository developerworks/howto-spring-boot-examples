package com.example.remoting

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(exclude = arrayOf(JmsAutoConfiguration::class))
@EnableScheduling
class SpringbootRemotingKotlinClientApplication {
}

fun main(args: Array<String>) {
    runApplication<SpringbootRemotingKotlinClientApplication>(*args)
}

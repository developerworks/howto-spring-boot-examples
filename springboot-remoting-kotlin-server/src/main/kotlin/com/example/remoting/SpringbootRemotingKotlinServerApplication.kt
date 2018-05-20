package com.example.remoting

import com.example.remoting.component.Runner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringbootRemotingKotlinServerApplication {

    @Bean
    fun runner(): Runner {
        return Runner()
    }
}

fun main(args: Array<String>) {
    runApplication<SpringbootRemotingKotlinServerApplication>(*args)
}


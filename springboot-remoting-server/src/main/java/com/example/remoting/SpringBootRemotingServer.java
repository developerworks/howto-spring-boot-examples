package com.example.remoting;

import com.example.remoting.component.Runner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SpringBootRemotingServer {

    public static void main(String[] args) {
        // TODO: rabbitmq
        // TODO: thrift
        // TODO: memcached
        // TODO: redis
        // TODO: dubbo
        // TODO: grpc
        // TODO: sofa-rpc
        // TODO: sofa-bolt
        SpringApplication.run(SpringBootRemotingServer.class, args);
    }

    @Bean
    Runner runner() {
        return new Runner();
    }

}

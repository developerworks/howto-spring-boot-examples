package com.example.embeddedactivemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;

@Slf4j
public class Runner implements CommandLineRunner {

  @Autowired
  JmsTemplate jmsTemplate;

  @Override
  public void run(String... args) throws Exception {
    log.info("Sending a email");
    jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hello"));
  }
}

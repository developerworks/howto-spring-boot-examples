package com.example.remoting

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Runner : CommandLineRunner {
  private val log = LoggerFactory.getLogger(Runner::class.java)

  @Autowired
  private var jmsInvoker: DnsService? = null

  @Scheduled(fixedRate = 1000L)
  fun query() {
    log.info("execute query...")
    log.info(">>>>>>>>>>>>>>>. {}", jmsInvoker.toString())
    log.info("[JMS         ] DNS Result: {}", jmsInvoker?.getName(""))
  }

  override fun run(vararg args: String?) {
    query()
//    log.info("[JMS         ] DNS Result: {}", jmsInvoker?.getName(""))
  }
}

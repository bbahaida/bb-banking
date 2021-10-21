package com.github.bbahaida.bbbanking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BbBankingApplication

fun main(args: Array<String>) {
	runApplication<BbBankingApplication>(*args)
}

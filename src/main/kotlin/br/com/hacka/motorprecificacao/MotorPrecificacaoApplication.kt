package br.com.hacka.motorprecificacao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


//Controlador responsável por consultar endereços a partir de um CEP.

@SpringBootApplication
class MotorPrecificacaoApplication

fun main(args: Array<String>) {
    runApplication<MotorPrecificacaoApplication>(*args)
}

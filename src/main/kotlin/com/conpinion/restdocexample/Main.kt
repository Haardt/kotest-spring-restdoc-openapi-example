package com.conpinion.restdocexample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication(scanBasePackages = ["com.conpinion.restdocexample"])
@EnableWebFlux
class SpringRestDocExampleApp



fun main(args: Array<String>) {
    println("Profile service started")
    SpringApplication(SpringRestDocExampleApp::class.java).run(*args, String.format("--server.port=%d", 8080))
}
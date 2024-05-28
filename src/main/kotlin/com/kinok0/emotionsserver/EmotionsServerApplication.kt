package com.kinok0.emotionsserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmotionsServerApplication

fun main(args: Array<String>) {
    runApplication<EmotionsServerApplication>(*args)
}

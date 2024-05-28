package com.kinok0.emotionsserver.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import kotlin.random.Random

@Configuration
class ApplicationConfiguration {

    @Bean
    fun random() = Random

}
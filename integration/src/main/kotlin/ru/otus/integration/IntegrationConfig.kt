package ru.otus.integration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.dsl.MessageChannels
import org.springframework.messaging.SubscribableChannel

@Configuration
class IntegrationConfig {

    @Bean
    fun newBookChannel(): SubscribableChannel = MessageChannels.direct().get()

    @Bean
    fun bookChannel(): SubscribableChannel = MessageChannels.direct().get()

    @Bean
    fun bookFlow() = IntegrationFlows
        .from("newBookChannel")
        .handle("authorsService", "addAuthors")
        .handle("genresService", "addGenres")
        .handle("bookService", "save")
        .channel("bookChannel")
        .get()
}
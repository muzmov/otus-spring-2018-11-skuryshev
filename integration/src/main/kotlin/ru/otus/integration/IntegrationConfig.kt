package ru.otus.integration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageChannels
import org.springframework.messaging.SubscribableChannel

@Configuration
class IntegrationConfig {
    @Bean
    fun addAuthorsChannel(): SubscribableChannel = MessageChannels.direct().get()

    @Bean
    fun authorsAddedChannel(): SubscribableChannel = MessageChannels.direct().get()

    @Bean
    fun addGenresChannel(): SubscribableChannel = MessageChannels.direct().get()

    @Bean
    fun genresAddedChannel(): SubscribableChannel = MessageChannels.direct().get()
}
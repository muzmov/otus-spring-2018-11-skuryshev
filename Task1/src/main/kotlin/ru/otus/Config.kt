package ru.otus

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.otus.dao.ClasspathCsvQuestionDao

@Configuration
@ComponentScan
open class Config {

    @Bean
    open fun questionDao() = ClasspathCsvQuestionDao("/questions.csv")
}
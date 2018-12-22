package ru.otus

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.ResourceBundleMessageSource
import ru.otus.dao.ClasspathCsvQuestionDao
import java.util.*

@Configuration
@ComponentScan
@PropertySource("classpath:/application.properties")
open class Config {

    @Bean
    open fun messageSource() = ResourceBundleMessageSource().apply {
        setBasename("messages")
        setDefaultEncoding("windows-1251")
    }

    @Bean
    open fun defaultLocale(@Value("\${locale}") locale: String): Locale = Locale.forLanguageTag(locale)

    @Bean
    open fun questionDao() = ClasspathCsvQuestionDao("/questions.csv")
}
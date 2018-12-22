package ru.otus.pollserviceboot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource

@Configuration
class Config {

    @Autowired
    lateinit var props: YamlProps

    @Bean
    fun messageSource() = ResourceBundleMessageSource().apply {
        setBasename(props.i18n.bundle)
        setDefaultEncoding(props.i18n.encoding)
    }
}
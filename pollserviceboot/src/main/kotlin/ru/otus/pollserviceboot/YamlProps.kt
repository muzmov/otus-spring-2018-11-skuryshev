package ru.otus.pollserviceboot

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("props")
class YamlProps {
    lateinit var locale: String
    lateinit var path: String
    val i18n = I18N()

    class I18N {
        lateinit var bundle: String
        lateinit var encoding: String
    }
}

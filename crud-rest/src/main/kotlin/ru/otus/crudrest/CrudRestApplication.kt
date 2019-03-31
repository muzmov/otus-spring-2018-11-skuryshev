package ru.otus.crudrest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import ru.otus.crudrest.dao.AuthorRepository
import ru.otus.crudrest.model.Author

@SpringBootApplication
@EnableReactiveMongoRepositories
class CrudRestApplication {

    @Bean
    fun routerConfig(repository: AuthorRepository) = router {
        GET("/authors") {
            ok().contentType(APPLICATION_JSON).body(repository.findAll())
        }
        GET("/author/{id}") {
            ok().contentType(APPLICATION_JSON).body(repository.findById(it.pathVariable("id")))
        }
        POST("/author") {
            it.bodyToMono(Author::class.java).flatMap {
                ok().contentType(APPLICATION_JSON).body(repository.save(it))
            }.switchIfEmpty(notFound().build())
        }
        PUT("/author") {
            it.bodyToMono(Author::class.java).flatMap {
                ok().contentType(APPLICATION_JSON).body(repository.save(it.copy(id = null)))
            }.switchIfEmpty(badRequest().build())
        }
        DELETE("/author/{id}") {
            ok().contentType(APPLICATION_JSON).body(repository.deleteById(it.pathVariable("id")))
        }
        resources("/**", ClassPathResource("public/"))
    }
}

fun main(args: Array<String>) {
    runApplication<CrudRestApplication>(*args)
}

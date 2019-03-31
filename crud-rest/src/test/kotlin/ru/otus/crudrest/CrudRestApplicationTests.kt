package ru.otus.crudrest

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.crudrest.dao.AuthorRepository
import ru.otus.crudrest.model.Author

@RunWith(SpringRunner::class)
@WebFluxTest
class CrudRestApplicationTests {

    @Autowired
    lateinit var router: RouterFunction<ServerResponse>

    @MockBean
    lateinit var authorRepository: AuthorRepository

    @Test
    fun testGetAll() {
        whenever(authorRepository.findAll()).thenReturn(Flux.just(Author(id = "1"), Author(id = "2")))
        val client = WebTestClient.bindToRouterFunction(router).build()
        client.get().uri("/authors").exchange().expectBody().json("""[{"id":"2"}, {"id":"1"}]""")
    }

    @Test
    fun testGetOne() {
        whenever(authorRepository.findById("1")).thenReturn(Mono.just(Author(id = "1")))
        val client = WebTestClient.bindToRouterFunction(router).build()
        client.get().uri("/author/1").exchange().expectBody().json("""{"id":"1"}""")
    }

    @Test
    fun testDelete() {
        whenever(authorRepository.deleteById("1")).thenReturn(Mono.empty())
        val client = WebTestClient.bindToRouterFunction(router).build()
        client.delete().uri("/author/1").exchange()
        verify(authorRepository, times(1)).deleteById("1")
    }

    @Test
    fun testPut() {
        whenever(authorRepository.save(any())).thenReturn(Mono.empty())
        val client = WebTestClient.bindToRouterFunction(router).build()
        client.put().uri("/author").body(Mono.just(Author(id = "1", firstName = "asd")), Author::class.java).exchange()
        val argumentCaptor = ArgumentCaptor.forClass(Author::class.java)
        verify(authorRepository, times(1)).save(argumentCaptor.capture())
        assertThat(argumentCaptor.value).isEqualTo(Author(id = null, firstName = "asd"))
    }

    @Test
    fun testPost() {
        whenever(authorRepository.save(any())).thenReturn(Mono.empty())
        val client = WebTestClient.bindToRouterFunction(router).build()
        client.post().uri("/author").body(Mono.just(Author(id = "1", firstName = "asd")), Author::class.java).exchange()
        val argumentCaptor = ArgumentCaptor.forClass(Author::class.java)
        verify(authorRepository, times(1)).save(argumentCaptor.capture())
        assertThat(argumentCaptor.value).isEqualTo(Author(id = "1", firstName = "asd"))
    }

}

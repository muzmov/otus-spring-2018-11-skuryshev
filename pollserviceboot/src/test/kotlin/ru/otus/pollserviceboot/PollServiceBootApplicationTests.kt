package ru.otus.pollserviceboot

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.shell.jline.InteractiveShellApplicationRunner
import org.springframework.test.context.junit4.SpringRunner
import ru.otus.pollserviceboot.service.PollService
import ru.otus.pollserviceboot.service.Reader
import ru.otus.pollserviceboot.service.Writer

@RunWith(SpringRunner::class)
@SpringBootTest(properties = [InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"])
class PollServiceBootApplicationTests {

    @MockBean
    lateinit var writer: Writer
    @MockBean
    lateinit var reader: Reader

    @Autowired
    lateinit var pollService: PollService

    @Test
    fun testPoll() {
        whenever(reader.readLine()).thenReturn("name").thenReturn("answer1").thenReturn("incorrect_answer2")

        pollService.doPoll()

        val argumentCaptor1 = argumentCaptor<String>()
        val argumentCaptor2 = argumentCaptor<Array<Any>>()
        verify(writer, times(4)).write(argumentCaptor1.capture(), argumentCaptor2.capture())
        Assertions.assertThat(argumentCaptor1.allValues).containsExactly("nameRequest", "question1", "question2", "result")
        Assertions.assertThat(argumentCaptor2.allValues)
            .containsExactly(arrayOf(), arrayOf(), arrayOf(), arrayOf("name", 1, 2))
    }
}


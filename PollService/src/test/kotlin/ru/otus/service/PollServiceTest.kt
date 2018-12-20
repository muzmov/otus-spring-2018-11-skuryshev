package ru.otus.service

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import ru.otus.dao.QuestionsDao
import ru.otus.model.Question

class PollServiceTest {

    @Test
    fun testPoll() {
        val writer = mock<Writer>()
        val questionsDao = mock<QuestionsDao> {
            on(it.loadQuestions()) doReturn listOf(
                Question("question1", "answer1"),
                Question("question2", "answer2")
            )
        }
        val reader = mock<Reader> {
            on(it.readLine()) doReturn "name" doReturn "answer1" doReturn "incorrect_answer2"
        }

        val pollService = PollService(writer, reader, questionsDao)

        pollService.doPoll()

        val argumentCaptor1 = argumentCaptor<String>()
        val argumentCaptor2 = argumentCaptor<Array<Any>>()
        verify(writer, times(4)).write(argumentCaptor1.capture(), argumentCaptor2.capture())
        assertThat(argumentCaptor1.allValues).containsExactly("nameRequest", "question1", "question2", "result")
        assertThat(argumentCaptor2.allValues).containsExactly(arrayOf(), arrayOf(), arrayOf(), arrayOf("name", 1, 2))
    }
}
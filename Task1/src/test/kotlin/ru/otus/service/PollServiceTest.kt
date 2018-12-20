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

        val argumentCaptor = argumentCaptor<String>()
        verify(writer, times(4)).write(argumentCaptor.capture())
        assertThat(argumentCaptor.allValues).containsExactly("Please enter your name:", "question1", "question2", "name. Correct answers: 1 out of 2")
    }
}
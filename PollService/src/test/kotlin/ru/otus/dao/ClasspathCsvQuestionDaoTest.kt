package ru.otus.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import ru.otus.model.Question

class ClasspathCsvQuestionDaoTest {
    @Test
    fun testQuestionsLoad() {
        val questions = ClasspathCsvQuestionDao("/questions_test.csv").loadQuestions()
        assertThat(questions).containsExactly(
            Question("question1?", "answer1"),
            Question("question2?", "answer2")
        )
    }
}
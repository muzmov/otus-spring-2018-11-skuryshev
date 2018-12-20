package ru.otus.service

import ru.otus.dao.QuestionsDao
import ru.otus.reader.Reader
import ru.otus.writer.Writer

class PollService(
    val writer: Writer,
    val reader: Reader,
    val questionsDao: QuestionsDao
) {

    fun doPoll() {
        writer.write("Please enter your name:")
        val name = reader.readLine()
        val questions = questionsDao.loadQuestions()
        var correctAnswers = 0
        questions.forEach{
            writer.write(it.question)
            if (reader.readLine() == it.answer) correctAnswers++
        }
        writer.write("$name. Correct answers: $correctAnswers out of ${questions.size}")
    }
}
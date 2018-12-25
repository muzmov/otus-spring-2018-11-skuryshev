package ru.otus.pollserviceboot.service

import org.springframework.stereotype.Service
import ru.otus.pollserviceboot.dao.QuestionsDao

@Service
class PollService(
    val writer: Writer,
    val reader: Reader,
    val questionsDao: QuestionsDao
) {

    fun doPoll() {
        writer.write("nameRequest", arrayOf())
        val name = reader.readLine()
        doPollInternal(name)
    }

    fun doPoll(name: String) {
        doPollInternal(name)
    }

    private fun doPollInternal(name: String) {
        val questions = questionsDao.loadQuestions()
        var correctAnswers = 0
        questions.forEach {
            writer.write(it.question, arrayOf())
            if (reader.readLine() == it.answer) correctAnswers++
        }
        writer.write("result", arrayOf(name, correctAnswers, questions.size))
    }
}
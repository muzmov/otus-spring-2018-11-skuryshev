package ru.otus.dao

import ru.otus.model.Question

class ClasspathCsvQuestionDao(private val path: String): QuestionsDao {

    override fun loadQuestions() = javaClass.getResource(path).readText().lines().map {
        Question(it.substringBefore(";"), it.substringAfter(";"))
    }
}
package ru.otus.pollserviceboot.dao

import ru.otus.pollserviceboot.model.Question

interface QuestionsDao {
    fun loadQuestions(): List<Question>
}
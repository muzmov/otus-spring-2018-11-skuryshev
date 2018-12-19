package ru.otus.dao

import ru.otus.model.Question

interface QuestionsDao {
    fun loadQuestions(): List<Question>
}
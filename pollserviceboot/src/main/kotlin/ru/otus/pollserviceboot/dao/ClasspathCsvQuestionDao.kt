package ru.otus.pollserviceboot.dao

import org.springframework.stereotype.Service
import ru.otus.pollserviceboot.YamlProps
import ru.otus.pollserviceboot.model.Question

@Service
class ClasspathCsvQuestionDao(private val props: YamlProps): QuestionsDao {

    override fun loadQuestions() = javaClass.getResource(props.path).readText().lines().map {
        Question(it.substringBefore(";"), it.substringAfter(";"))
    }
}
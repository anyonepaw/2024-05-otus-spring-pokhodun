package ru.otus.spring.service.converter

import ru.otus.spring.domain.Question
import ru.otus.spring.service.util.Const.COMMA_REGEX
import ru.otus.spring.service.util.Const.QUESTION_REGEX

class QuestionConverterImpl : QuestionConverter {
    override fun convertToEntity(list: List<String>): List<Any> {
        val questionsToAnswers = list.joinToString(separator = "").split(";") - listOf("")
        return questionsToAnswers.map { it.split(QUESTION_REGEX) }.windowed(1).mapIndexed { index, element ->
            val pair = element.flatten()
            Question(id = index + 1, question = "${pair[0]}?", answers = pair[1].split(COMMA_REGEX))
        }
    }

    override fun convertEntityToString(entities: List<Any>): String {
        @Suppress("UNCHECKED_CAST")
        val questions = entities as List<Question>
        return buildString {
            questions.forEach {
                this.append("${it.id}. ${it.question}")
                it.answers.forEach { answer ->
                    this.append("$answer\n")
                }
                this.append("\n")
            }
        }
    }

}

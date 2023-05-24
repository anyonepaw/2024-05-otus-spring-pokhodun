package ru.otus.spring.service.converter

import org.springframework.stereotype.Component
import ru.otus.spring.domain.Question
import ru.otus.spring.service.config.QuestionConfig
import ru.otus.spring.service.util.Const.COMMA_REGEX
import ru.otus.spring.service.util.Const.QUESTION_REGEX

@Component
class QuestionConverterImpl(
    val config: QuestionConfig,
) : Converter {
    override fun convertToEntity(list: List<String>): List<Any> {
        val questionsToAnswers = list.joinToString(separator = "").split(";") - listOf("")
        return questionsToAnswers.map { it.split(QUESTION_REGEX) }.windowed(1).mapIndexed { index, element ->
            val pair = element.flatten()
            Question(
                id = index + 1,
                question = "${pair[0]}?",
                answers = pair[1].split(COMMA_REGEX).filterNot { it == "" })
        }
    }

    override fun convertEntityToString(entities: List<*>): String {
        val questions = entities.map { it as Question }
        return buildString {
            questions.forEach {
                this.append("\n${it.id}. ${it.question} \n")
                it.answers.forEachIndexed { index, answer ->
                    this.append("${config.answersIdx[index]}. $answer\n")
                }
                this.append("\n")
            }
        }
    }


}

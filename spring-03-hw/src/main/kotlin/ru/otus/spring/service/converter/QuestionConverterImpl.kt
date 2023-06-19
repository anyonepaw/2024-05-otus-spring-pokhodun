package ru.otus.spring.service.converter

import org.springframework.stereotype.Component
import ru.otus.spring.domain.Question
import ru.otus.spring.service.config.QuestionProperties
import ru.otus.spring.service.util.Const.COMMA_REGEX
import ru.otus.spring.service.util.Const.NEW_LINE
import ru.otus.spring.service.util.Const.QUESTION_REGEX

@Component
class QuestionConverterImpl(
    private val questionProperties: QuestionProperties,
) : Converter {
    override fun convertToEntity(list: List<String>): List<Any> {
        val questionsToAnswers = list.joinToString(separator = "").split(";") - listOf("")
        return questionsToAnswers.map { it.split(QUESTION_REGEX) }.windowed(1).mapIndexed { index, element ->
            val pair = element.flatten()
            Question(
                id = index + 1,
                question = "${pair[0].trim()}?",
                answers = pair[1].split(COMMA_REGEX).filterNot { it == "" })
        }
    }

    override fun convertEntityToString(entities: List<*>): String {
        val questions = entities.map { it as Question }
        return buildString {
            questions.forEach {
                this.append("$NEW_LINE${it.id}. ${it.question} $NEW_LINE")
                it.answers.forEachIndexed { index, answer ->
                    this.append("${questionProperties.idx?.get(index)}. $answer$NEW_LINE")
                }
                this.append(NEW_LINE)
            }
        }
    }

}

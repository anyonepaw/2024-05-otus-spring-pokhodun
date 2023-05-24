package ru.otus.spring.service

import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.otus.spring.domain.Question
import ru.otus.spring.service.config.QuestionConfig
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ReaderService


@Service
class QuestionServiceImpl(
    @Qualifier("csvReaderServiceImpl")
    private val reader: ReaderService,
    @Qualifier("questionConverterImpl")
    private val questionConverter: Converter,
    private val config: QuestionConfig,
) : QuestionService {

    override fun getQuestions(): List<Question> {
        val resource = readResource(config.questionsResource)
        if (resource.isEmpty()) return listOf()
        val questionList = questionConverter.convertToEntity(resource).map { it as Question }
        val questionToAnswerMap = getAnswers()

        questionList.forEach { question ->
            questionToAnswerMap[question.question]?.let {
                question.correctAnswers.addAll(it)
            }
        }

        return questionList
    }

    private fun getAnswers(): Map<String, List<String>> {
        val resource = readResource(config.answersResource)
        if (resource.isEmpty()) return mapOf()
        return questionConverter.convertToEntity(resource)
            .map { it as Question }.associate { it.question to it.answers }
    }

    private fun readResource(resource: Resource): List<String> {
        var readList: List<String> = mutableListOf()
        try {
            readList = reader.read(resource).map { it as String }
        } catch (e: Exception) {
            logger.error("File reading error: $e")
        }
        return readList
    }

    private companion object : KLogging()

}

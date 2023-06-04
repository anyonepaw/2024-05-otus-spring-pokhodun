package ru.otus.spring.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.otus.spring.domain.Question
import ru.otus.spring.service.config.QuestionConfig
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ResourceReader

@Service
class AnswerServiceImpl(
    private val config: QuestionConfig,
    @Qualifier("csvReaderServiceImpl")
    private val resourceReader: ResourceReader,
    @Qualifier("questionConverterImpl")
    private val questionConverter: Converter,
) : AnswerService {
    override fun getAnswers(): Map<String, List<String>> {
        val resource = resourceReader.readResource(config.answersResource)
        if (resource.isEmpty()) return mapOf()
        return questionConverter.convertToEntity(resource)
            .map { it as Question }.associate { it.question to it.answers }
    }
}

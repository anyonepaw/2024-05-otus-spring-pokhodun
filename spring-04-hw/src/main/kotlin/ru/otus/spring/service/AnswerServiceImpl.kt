package ru.otus.spring.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.otus.spring.domain.Question
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ReaderService

@Service
class AnswerServiceImpl(
    private val quizLocalizationService: QuizLocalizationService,
    @Qualifier("resourceBundleReaderServiceImpl")
    private val reader: ReaderService,
    @Qualifier("questionConverterImpl")
    private val converter: Converter,
) : AnswerService {
    override fun getAnswers(): Map<String, List<String>> {
        val resource = reader.read(quizLocalizationService.getAnswers())
        if (resource.isEmpty()) return mapOf()
        return converter.convertToEntity(resource.map { it as String })
            .map { it as Question }.associate { it.question to it.answers }
    }
}

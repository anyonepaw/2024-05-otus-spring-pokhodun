package ru.otus.spring.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.otus.spring.domain.Question
import ru.otus.spring.service.config.QuestionProperties
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ReaderService


@Service
class QuestionServiceImpl(
    @Qualifier("resourceBundleReaderServiceImpl")
    private val reader: ReaderService,
    @Qualifier("questionConverterImpl")
    private val questionConverter: Converter,
    private val quizLocalizationService: QuizLocalizationService,
    private val answerService: AnswerService,
) : QuestionService {

    override fun getQuestions(): List<Question> {
        val resource = reader.read(quizLocalizationService.getQuestions())
        if (resource.isEmpty()) return listOf()
        val questionList = questionConverter.convertToEntity(resource.map { it as String })
            .map { it as Question }
        val questionToAnswerMap = answerService.getAnswers()

        questionList.forEach { question ->
            questionToAnswerMap[question.question]?.let {
                question.correctAnswers.addAll(it)
            }
        }

        return questionList
    }

}

package ru.otus.spring.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.otus.spring.domain.Question
import ru.otus.spring.service.config.QuestionConfig
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ResourceReader


@Service
class QuestionServiceImpl(
    @Qualifier("csvReaderServiceImpl")
    private val resourceReader: ResourceReader,
    @Qualifier("questionConverterImpl")
    private val questionConverter: Converter,
    private val config: QuestionConfig,
    private val answerService: AnswerService,
) : QuestionService {

    override fun getQuestions(): List<Question> {
        val resource = resourceReader.readResource(config.questionsResource)
        if (resource.isEmpty()) return listOf()
        val questionList = questionConverter.convertToEntity(resource).map { it as Question }
        val questionToAnswerMap = answerService.getAnswers()

        questionList.forEach { question ->
            questionToAnswerMap[question.question]?.let {
                question.correctAnswers.addAll(it)
            }
        }

        return questionList
    }

}

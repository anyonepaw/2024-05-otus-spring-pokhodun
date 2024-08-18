package ru.otus.spring.service

import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.otus.spring.dao.SimpleDaoImpl
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
    private val consoleAnswersDao: SimpleDaoImpl<Int, List<String>>,
    questionProperties: QuestionProperties,
) : QuestionService {

    val alphabetList: List<String> = questionProperties.idx?.split("")?.filterNot { it == "" }.orEmpty()
    val passingScore: Int = questionProperties.score

    fun buildPersonName(array: Collection<*>) = buildString { array.forEach { this.append("$it") } }

    fun getLocalizedQuestionsMap(): List<String> =
        getQuestions().map { questionConverter.convertEntityToString(listOf(it)) }

    fun saveAnswers(questionId: Int, consoleAnswers: Collection<*>) {
        consoleAnswersDao.save(questionId,
            consoleAnswers.map { "$it".lowercase() }.filter { alphabetList.contains(it) })
    }

    fun countScore(): Int {
        val consoleAnswersFromDB = consoleAnswersDao.get()
        if (consoleAnswersFromDB.values.isEmpty()) return 0

        var resultScore = 0

        getQuestions().forEach { question ->
            val consoleAnswers = consoleAnswersFromDB[question.id].orEmpty()
            if (consoleAnswers.size <= question.answers.size) {
                val answersIdxList = consoleAnswers.map { alphabetList.indexOf(it) }
                val resultAnswers = answersIdxList.map { question.answers.getOrNull(it) }
                if (CollectionUtils.isEqualCollection(resultAnswers, question.correctAnswers)) {
                    resultScore++
                }
            }
        }
        return resultScore
    }

    fun getLastAnswers(): Map<String, List<String>> {
        val consoleAnswersFromDB = consoleAnswersDao.get()
        if (consoleAnswersFromDB.values.isEmpty()) return emptyMap()

        val resultMap = mutableMapOf<String, List<String>>()
        getQuestions().forEach { question ->
            val consoleAnswers = consoleAnswersFromDB[question.id].orEmpty()
            val answersIdxList = consoleAnswers.map { alphabetList.indexOf(it) }
            val resultAnswers = answersIdxList.mapNotNull { question.answers.getOrNull(it) }
            resultMap[question.question] = resultAnswers
        }
        return resultMap
    }

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

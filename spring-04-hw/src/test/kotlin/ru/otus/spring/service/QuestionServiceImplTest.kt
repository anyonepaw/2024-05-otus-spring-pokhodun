package ru.otus.spring.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import ru.otus.spring.SpringBootTestBase
import ru.otus.spring.dao.SimpleDaoImpl
import ru.otus.spring.domain.Question
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ReaderService
import util.Consts.ANSWER_1
import util.Consts.ANSWER_2
import util.Consts.ANSWER_3
import util.Consts.ANSWER_4
import util.Consts.QUESTION
import util.Consts.RIGHT_ANSWER

class QuestionServiceImplTest : SpringBootTestBase() {

    @MockBean(name = "questionConverterImpl")
    private lateinit var converter: Converter

    @MockBean(name = "resourceBundleReaderServiceImpl")
    private lateinit var readerService: ReaderService

    @MockBean
    private lateinit var quizLocalizationService: QuizLocalizationService

    @MockBean
    private lateinit var answerService: AnswerService

    @MockBean
    private lateinit var consoleAnswersDao: SimpleDaoImpl<Int, List<String>>

    @Autowired
    private lateinit var questionServiceImpl: QuestionServiceImpl

    @BeforeEach
    fun before() {
        val preparedList = listOf(QUESTION)
        `when`(readerService.read(any())).thenReturn(preparedList)
        `when`(converter.convertToEntity(preparedList))
            .thenReturn(listOf(Question(1, QUESTION, listOf(ANSWER_1, ANSWER_2, ANSWER_3, ANSWER_4))))
        `when`(answerService.getAnswers()).thenReturn(mapOf(QUESTION to listOf(RIGHT_ANSWER)))
    }

    @Test
    fun `save questions - success`() {
        questionServiceImpl.saveAnswers(1, listOf("a"))
        verify(consoleAnswersDao).save(1, listOf("a"))
    }

    @Test
    fun `build person name - success`() {
        assertEquals(
            "FirstName LastName",
            questionServiceImpl.buildPersonName(listOf("FirstName", " ", "LastName"))
        )
    }

    @Test
    fun `get questions - success`() {
        val result = questionServiceImpl.getQuestions()
        assertEquals(1, result.size)
        assertEquals(QUESTION, result[0].question)
        assertEquals(listOf(ANSWER_1, ANSWER_2, ANSWER_3, ANSWER_4), result[0].answers)
        assertEquals(RIGHT_ANSWER, result[0].correctAnswers[0])
    }

    @Test
    fun `get countScore - success`() {
        `when`(consoleAnswersDao.get()).thenReturn(mapOf(1 to listOf("a")))
        val resultScore = questionServiceImpl.countScore()
        assertEquals(1, resultScore)
    }

    @Test
    fun `get countScore - no right answers`() {
        `when`(consoleAnswersDao.get()).thenReturn(mapOf(1 to listOf("a", "a")))
        val resultScore = questionServiceImpl.countScore()
        assertEquals(0, resultScore)
    }

    @Test
    fun `get countScore - too much answers`() {
        `when`(consoleAnswersDao.get()).thenReturn(mapOf(1 to listOf("a", "a", "a", "a", "a")))
        val resultScore = questionServiceImpl.countScore()
        assertEquals(0, resultScore)
    }

    @Test
    fun `get last answers - success`() {
        `when`(consoleAnswersDao.get()).thenReturn(mapOf(1 to listOf("a")))
        val result = questionServiceImpl.getLastAnswers()
        assertEquals(1, result.size)
        assertEquals(listOf(RIGHT_ANSWER), result[QUESTION])
    }

    @Test
    fun `get last answers - empty list`() {
        `when`(consoleAnswersDao.get()).thenReturn(mapOf(1 to listOf("wrong")))
        val result = questionServiceImpl.getLastAnswers()
        assertEquals(1, result.size)
        assertEquals(emptyList<String>(), result[QUESTION])
    }

}

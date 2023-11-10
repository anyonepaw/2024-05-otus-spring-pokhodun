package ru.otus.spring.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import ru.otus.spring.SpringBootTestBase
import ru.otus.spring.domain.Question
import ru.otus.spring.service.converter.QuestionConverterImpl
import ru.otus.spring.service.reader.CsvReaderServiceImpl


class QuestionServiceImplTest : SpringBootTestBase() {

    @InjectMocks
    private lateinit var questionServiceImpl: QuestionServiceImpl

    @Mock
    private lateinit var csvReaderServiceImpl: CsvReaderServiceImpl

    @Mock
    private lateinit var questionConverterImpl: QuestionConverterImpl

    @Mock
    private lateinit var quizLocalizationService: QuizLocalizationService

    @Mock
    private lateinit var answerServiceImpl: AnswerServiceImpl

    @Test
    fun `get questions - success`() {
        val preparedList = listOf("Question?", "Answer")
        `when`(csvReaderServiceImpl.read(any())).thenReturn(preparedList)
        `when`(questionConverterImpl.convertToEntity(preparedList)).thenReturn(
            listOf(Question(1, "Question?", listOf("Answer")))
        )
        assertEquals(1, questionServiceImpl.getQuestions().size)
    }

}

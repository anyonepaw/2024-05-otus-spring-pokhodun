package ru.otus.spring.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.otus.spring.domain.Question
import ru.otus.spring.service.config.QuestionConfig
import ru.otus.spring.service.converter.QuestionConverterImpl
import ru.otus.spring.service.reader.CsvReaderServiceImpl


@ExtendWith(MockitoExtension::class)
class QuestionServiceImplTest {

    @InjectMocks
    private lateinit var questionServiceImpl: QuestionServiceImpl

    @Mock
    private lateinit var csvReaderServiceImpl: CsvReaderServiceImpl

    @Mock
    private lateinit var questionConverterImpl: QuestionConverterImpl

    @Mock
    private lateinit var questionConfig: QuestionConfig

    @Test
    fun `get questions - success`() {
        val preparedList = listOf("Question?", "Answer")
        `when`(csvReaderServiceImpl.read(any())).thenReturn(preparedList)
        `when`(questionConverterImpl.convertToEntity(preparedList)).thenReturn(
            listOf(Question(1, "Question?", listOf("Answer")))
        )
        assertEquals(1, questionServiceImpl.getQuestions().size)
    }

    @Test
    fun `get questions - exception`() {
        `when`(csvReaderServiceImpl.read(any())).thenThrow(IllegalArgumentException::class.java)
         assertEquals(0, questionServiceImpl.getQuestions().size)
    }

}

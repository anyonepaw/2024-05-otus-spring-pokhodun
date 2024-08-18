package ru.otus.spring.service.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.spring.SpringBootTestBase
import ru.otus.spring.domain.Question
import util.Consts.ANSWER_1
import util.Consts.ANSWER_2
import util.Consts.ANSWER_3
import util.Consts.ANSWER_4
import util.Consts.FULL_QUESTION
import util.Consts.QUESTION
import util.Consts.getQuestionEntity

class QuestionConverterImplTest : SpringBootTestBase() {

    @Autowired
    private lateinit var questionConverterImpl: QuestionConverterImpl


    @Test
    fun convertToEntity() {
        val questionEntity =
            questionConverterImpl.convertToEntity(listOf(FULL_QUESTION))[0]
        assertNotNull(questionEntity)
        assertTrue(questionEntity is Question)
        with(questionEntity as Question) {
            assertEquals(1, id)
            assertEquals(QUESTION, question)
            assertEquals(listOf(ANSWER_1, ANSWER_2, ANSWER_3, ANSWER_4), answers)
        }
    }

    @Test
    fun convertQuestionToString() {
        val questionEntity = getQuestionEntity()
        val result = questionConverterImpl.convertEntityToString(listOf(questionEntity))
        assertNotNull(result)
        assertEquals("""
                1. What is the default scope of the Spring Bean?
                a. singleton
                b. prototype
                c. request
                d. session


                """.trimIndent(), result)
    }
}
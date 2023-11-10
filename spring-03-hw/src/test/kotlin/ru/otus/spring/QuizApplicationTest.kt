package ru.otus.spring

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext


class QuizApplicationTest : SpringBootTestBase() {

    @Test
    fun `context loads`(context: ApplicationContext) {
        assertNotNull(context)
    }

}

package ru.otus.spring
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext


class QuizApplicationTest {

    @Test
    fun `context loads`() {
        val ctx = AnnotationConfigApplicationContext(QuizApplication::class.java)
        assertNotNull(ctx)
    }

}

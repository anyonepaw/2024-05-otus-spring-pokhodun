package ru.otus.spring
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.context.support.ClassPathXmlApplicationContext


class QuizApplicationTest {

    @Test
    fun `context loads`() {
        val ctx = ClassPathXmlApplicationContext("spring-context.xml")
        assertNotNull(ctx)
    }

}

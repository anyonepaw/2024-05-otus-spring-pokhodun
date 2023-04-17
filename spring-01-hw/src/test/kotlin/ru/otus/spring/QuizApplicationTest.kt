package ru.otus.spring
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.spring.service.PersonService


class QuizApplicationTest {
    @Test
    fun `context loads`() {
        val ctx = ClassPathXmlApplicationContext("spring-context.xml")
        assertNotNull(ctx)
    }

    @Test
    fun `when context is up - returns bean`() {
        val ctx = ClassPathXmlApplicationContext("spring-context.xml")
        val service: PersonService = ctx.getBean(PersonService::class.java)
        assertNotNull(service)
    }

}

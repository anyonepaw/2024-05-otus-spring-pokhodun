package ru.otus.spring.service

import java.util.Scanner
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.context.support.ClassPathXmlApplicationContext

class PersonServiceImplTest {

    @Test
    fun getSource() {
        val ctx = ClassPathXmlApplicationContext("spring-context.xml")
        val service: PersonService = ctx.getBean(PersonService::class.java)

        val expected = Scanner(ctx.getResource("/quiz.csv").file).useDelimiter("\\Z").next()
        val actual = service.getSource()
        assertEquals(expected, actual)
    }

}

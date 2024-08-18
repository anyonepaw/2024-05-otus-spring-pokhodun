package ru.otus.spring.dao

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import ru.otus.spring.SpringBootTestBase

class ConsoleAnswersDaoImplTest: SpringBootTestBase() {

    @Autowired
    private lateinit var consoleAnswersDaoImpl: SimpleDaoImpl<Int, List<String>>

    @Test
    fun save() {
        consoleAnswersDaoImpl.save(1, listOf("a", "b", "c"))
        val result = consoleAnswersDaoImpl.get()
        assertEquals(1, result.size)
        assertEquals(listOf("a", "b", "c"), result[1])
    }

    @Test
    fun clear() {
        consoleAnswersDaoImpl.save(1, listOf("a", "b", "c"))
        val result = consoleAnswersDaoImpl.get()
        assertEquals(1, result.size)
        consoleAnswersDaoImpl.clear()
        val cleaned = consoleAnswersDaoImpl.get()
        assertEquals(0, cleaned.size)
    }
}
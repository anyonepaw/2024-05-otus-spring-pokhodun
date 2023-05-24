package ru.otus.spring.service.reader

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = [CsvReaderServiceImpl::class])
class CsvReaderServiceImplTest {

    @Autowired
    private lateinit var csvReaderServiceImpl: ReaderService
    @Value("\${questions.resource.path}")
    private lateinit var questionsResource: Resource

    @Test
    fun read() {
       assertNotNull(csvReaderServiceImpl.read(questionsResource))
    }
}

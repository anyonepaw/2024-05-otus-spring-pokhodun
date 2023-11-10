package ru.otus.spring.service.reader

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import ru.otus.spring.SpringBootTestBase
import ru.otus.spring.service.config.ApplicationConfig


class CsvReaderServiceImplTest : SpringBootTestBase() {

    @Autowired
    private lateinit var csvReaderServiceImpl: ReaderService

    @Autowired
    private lateinit var messageSource: MessageSource

    @Autowired
    private lateinit var applicationConfig: ApplicationConfig

    @Test
    fun read() {
        assertNotNull(
            csvReaderServiceImpl.read(
                messageSource.getMessage(
                    "questions",
                    arrayOf(),
                    applicationConfig.locale
                )
            )
        )
    }
}

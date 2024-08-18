package ru.otus.spring.service.reader

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import ru.otus.spring.SpringBootTestBase
import ru.otus.spring.service.config.ApplicationConfig

class ResourceBundleReaderServiceImplTest : SpringBootTestBase() {

    @Autowired
    private lateinit var resourceBundleReaderServiceImpl: ReaderService

    @Autowired
    private lateinit var messageSource: MessageSource

    @Autowired
    private lateinit var applicationConfig: ApplicationConfig

    @Test
    fun read() {
        assertNotNull(
            resourceBundleReaderServiceImpl.read(
                messageSource.getMessage("questions", arrayOf(), applicationConfig.locale)
            )
        )
    }
}
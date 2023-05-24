package ru.otus.spring.service.reader

import com.opencsv.CSVReaderBuilder
import java.io.InputStreamReader
import mu.KLogging
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import ru.otus.spring.service.util.Const.MANY_WHITESPACES_REGEX

@Service
class CsvReaderServiceImpl: ReaderService {
    override fun read(resource: Any?): Collection<*> {
        val fileName = (resource as Resource).filename
        val resourceStream = javaClass.classLoader.getResourceAsStream(fileName)
            ?: throw IllegalArgumentException("Resource not found $resource")
        val reader = CSVReaderBuilder(InputStreamReader(resourceStream)).withSkipLines(1).build()
        return reader.use {
            reader.readAll()
        }.map { it.joinToString().trim().replace(MANY_WHITESPACES_REGEX, " ") }
    }

    private companion object : KLogging()

}

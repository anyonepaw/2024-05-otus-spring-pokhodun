package ru.otus.spring.service.reader

import com.opencsv.CSVReaderBuilder
import java.io.InputStreamReader
import org.springframework.core.io.Resource
import ru.otus.spring.service.util.Const.MANY_WHITESPACES_REGEX

class CsvReaderServiceImpl : ReaderService {
    override fun read(resource: Resource): List<String> {
        val fileName = resource.filename
        val resourceStream = javaClass.classLoader.getResourceAsStream(fileName)
            ?: throw IllegalArgumentException("Resource not found $resource")
        val reader = CSVReaderBuilder(InputStreamReader(resourceStream)).withSkipLines(1).build()
        return reader.use {
            reader.readAll()
        }.map { it.joinToString().trim().replace(MANY_WHITESPACES_REGEX, " ") }
    }
}

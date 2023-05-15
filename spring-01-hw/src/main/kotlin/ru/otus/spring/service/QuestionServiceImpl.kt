package ru.otus.spring.service

import mu.KLogging
import org.springframework.core.io.Resource
import ru.otus.spring.service.converter.QuestionConverterImpl
import ru.otus.spring.service.reader.ReaderService
import ru.otus.spring.service.writer.WriterService

class QuestionServiceImpl(
    private var reader: ReaderService,
    private var writer: WriterService,
    private var converter: QuestionConverterImpl
) : QuestionService {

    private var resource: Resource? = null

    fun setResource(resource: Resource?) {
        this.resource = resource
    }

    override fun execute() {
        var readList: List<String>? = mutableListOf()
        try {
            readList = resource?.let { reader.read(it) }
        } catch (e: Exception) {
            logger.error("File reading error: $e")
        }
        readList?.let {
            val quizList = converter.convertToEntity(it)
            val resultString = converter.convertEntityToString(quizList)
            writer.write(resultString)
        }
    }

    private companion object : KLogging()

}

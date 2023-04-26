package ru.otus.spring.service

import mu.KLogging
import org.springframework.core.io.Resource
import ru.otus.spring.service.converter.QuestionConverter
import ru.otus.spring.service.reader.ReaderService
import ru.otus.spring.service.writer.WriterService

class QuestionServiceImpl(
    private var reader: ReaderService,
    private var writer: WriterService,
    private var converter: QuestionConverter
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
            val quizList = converter.convertToQuestions(it)
            val resultString = converter.convertQuestionsToString(quizList)
            writer.write(resultString)
        }
    }

    private companion object : KLogging()

}

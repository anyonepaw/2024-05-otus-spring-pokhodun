package ru.otus.spring.service

import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.otus.spring.dao.SimpleDaoImpl
import ru.otus.spring.service.config.QuestionProperties
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ReaderService
import ru.otus.spring.service.util.Regexes.NEW_LINE
import ru.otus.spring.service.writer.WriterService


@Component
class QuestionOutputDispatcher(
    private val questionService: QuestionService,
    @Qualifier("consoleWriterServiceImpl")
    private val writerService: WriterService,
    private val questionConverter: Converter,
    @Qualifier("consoleReaderServiceImpl")
    private val consoleReader: ReaderService,
    private val questionProperties: QuestionProperties,
    private val quizLocalizationService: QuizLocalizationService,
    private val consoleAnswersDao: SimpleDaoImpl<Int, Collection<*>>,
) {

    fun introduce() {
        writerService.write(quizLocalizationService.introduction())
        startNewLine()
        val name = buildString { consoleReader.read().forEach { this.append("$it") } }
        writerService.write(quizLocalizationService.greet(name))
        startNewLine()
    }

    fun beginTest() {
        val questionList = questionService.getQuestions()
        questionList.forEach { question ->
            writerService.write(questionConverter.convertEntityToString(listOf(question)))
            val consoleAnswers = consoleReader.read()
            consoleAnswersDao.save(question.id, consoleAnswers)
        }
    }

    fun countScore() {
        val questionList = questionService.getQuestions()
        var resultScore = 0
        val answersIdxList = questionProperties.idx?.split("")?.filterNot { it == "" }


            val result = mutableListOf<String?>()

            consoleAnswersDao.get().forEach { consoleAnswer ->
                answersIdxList?.let {
                    if (answersIdxList.contains("$consoleAnswer")) {
                        val idx = answersIdxList.indexOf("$consoleAnswer")
                    }
                }
            }



        val passingScore = questionProperties.score?.toInt()
        passingScore?.let {
            writerService.write(
                quizLocalizationService.getResultMessage(
                    resultScore = resultScore,
                    questionsQuantity = questionList.size,
                    passingScore = passingScore
                )
            )
            startNewLine()
            if (resultScore >= passingScore) {
                writerService.write(quizLocalizationService.getWinnerResultMessage())
            } else {
                writerService.write(quizLocalizationService.getLooserResultMessage())
            }
            startNewLine()
            startNewLine()
        }
    }

    fun getAnswers() {
        val questionList = questionService.getQuestions()
        val answers: Map<Int, Collection<*>> = consoleAnswersDao.get()
        answers.forEach { (k, v) ->
            writerService.write("Question: ${questionList[k-1].question}, your answers: $v")
            startNewLine()
        }
    }

    private fun startNewLine() = writerService.write(NEW_LINE)
}

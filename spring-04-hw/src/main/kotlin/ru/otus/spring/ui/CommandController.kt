package ru.otus.spring.ui

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import ru.otus.spring.service.QuestionServiceImpl
import ru.otus.spring.service.QuizLocalizationService
import ru.otus.spring.service.reader.ReaderService
import ru.otus.spring.service.util.Regexes.NEW_LINE
import ru.otus.spring.service.writer.WriterService


@ShellComponent
class CommandController(
    private val questionServiceImpl: QuestionServiceImpl,
    @Qualifier("consoleWriterServiceImpl")
    private val writerService: WriterService,
    @Qualifier("consoleReaderServiceImpl")
    private val consoleReader: ReaderService,
    private val quizLocalizationService: QuizLocalizationService
) {

    @ShellMethod(key = ["start"])
    fun start() {
        writerService.write(quizLocalizationService.introduction())
        startNewLine()
        val name = questionServiceImpl.buildPersonName(consoleReader.read())
        writerService.write(quizLocalizationService.greet(name))
        startNewLine()
    }

    @ShellMethod(key = ["test"])
    fun beginTest() {
        val questionList = questionServiceImpl.getLocalizedQuestionsMap()
        questionList.forEachIndexed { idx, question ->
            writerService.write(question)
            val consoleAnswers = consoleReader.read()
            questionServiceImpl.saveAnswers(idx + 1, consoleAnswers)
        }
    }

    @ShellMethod(key = ["score"])
    fun score() {
        val resultScore = questionServiceImpl.countScore()
        val passingScore = questionServiceImpl.passingScore
        writerService.write(
            quizLocalizationService.getResultMessage(
                resultScore = resultScore,
                questionsQuantity = questionServiceImpl.getLocalizedQuestionsMap().size,
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

    @ShellMethod(key = ["answers"])
    fun getLastAnswers() {
       val questionToLastAnswersMap = questionServiceImpl.getLastAnswers()
        questionToLastAnswersMap.forEach { (k, v) ->
            writerService.write("${k}, your answers: $v")
            startNewLine()
        }
    }

    private fun startNewLine() = writerService.write(NEW_LINE)

}

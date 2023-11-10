package ru.otus.spring.service.executor

import java.util.*
import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import ru.otus.spring.domain.Question
import ru.otus.spring.service.QuestionService
import ru.otus.spring.service.QuizLocalizationService
import ru.otus.spring.service.config.QuestionProperties
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ReaderService
import ru.otus.spring.service.util.Const.NEW_LINE
import ru.otus.spring.service.writer.WriterService


@Component
@Profile("!test")
class QuestionExecutorImpl(
    private val questionService: QuestionService,
    @Qualifier("consoleWriterServiceImpl")
    private val writerService: WriterService,
    private val questionConverter: Converter,
    @Qualifier("consoleReaderServiceImpl")
    private val consoleReader: ReaderService,
    private val questionProperties: QuestionProperties,
    private val quizLocalizationService: QuizLocalizationService,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        writerService.write(quizLocalizationService.introduction())
        startNewLine()
        val name = buildString { consoleReader.read().forEach { this.append("$it") } }
        writerService.write(quizLocalizationService.greet(name))
        startNewLine()

        val questionList = questionService.getQuestions()

        val resultScore = determineCorrectAnswers(questionList)
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

    private fun determineCorrectAnswers(questionList: List<Question>): Int {
        var score = 0
        val answersIdxList = questionProperties.idx?.split("")?.filterNot { it == "" }

        questionList.forEach { question ->
            writerService.write(questionConverter.convertEntityToString(listOf(question)))
            val consoleAnswers = consoleReader.read()
            val resultAnswers = mutableListOf<String?>()

            consoleAnswers.forEach { consoleAnswer ->
                val answer = consoleAnswer.toString()
                answersIdxList?.let {
                    if (answersIdxList.contains(answer)) {
                        val idx = answersIdxList.indexOf(consoleAnswer.toString())
                        resultAnswers.add(question.answers.getOrNull(idx))
                    }
                }
            }
            if (CollectionUtils.isEqualCollection(question.correctAnswers, resultAnswers.filterNotNull())) {
                score++
            }
        }
        return score
    }

    private fun startNewLine() = writerService.write(NEW_LINE)
}

package ru.otus.spring.service.executor

import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.otus.spring.domain.Question
import ru.otus.spring.service.QuestionService
import ru.otus.spring.service.config.QuestionConfig
import ru.otus.spring.service.converter.Converter
import ru.otus.spring.service.reader.ReaderService
import ru.otus.spring.service.writer.WriterService


@Component
class QuestionExecutorImpl(
    private val questionService: QuestionService,
    @Qualifier("consoleWriterServiceImpl")
    private val writerService: WriterService,
    private val questionConverter: Converter,
    @Qualifier("consoleReaderServiceImpl")
    private val consoleReader: ReaderService,
    private val config: QuestionConfig,
) {
    fun executeQuiz() {
        writerService.write("Hello, respondent! Please type your full name: \n")
        consoleReader.read()

        val questionList = questionService.getQuestions()
        val passingScore = config.scores

        val scores = determineCorrectAnswers(questionList)

        writerService.write("\nYour results: $scores from ${questionList.size}. The passing score is $passingScore. \n")
        when {
            scores >= passingScore -> {
                writerService.write("My digital congrats! You passed this awesome test!")
            }

            else -> {
                writerService.write("That's a pity, you did not pass. Looking forward for a next try! \n")
            }
        }
    }

    private fun determineCorrectAnswers(questionList: List<Question>): Int {
        var scores = 0
        val answersIdxList = config.answersIdx.split("").filterNot { it == "" }

        questionList.forEach { question ->
            writerService.write(questionConverter.convertEntityToString(listOf(question)))
            val consoleAnswers = consoleReader.read()
            val resultAnswers = mutableListOf<String?>()

            consoleAnswers.forEach {
                val answer = it.toString()
                if (answersIdxList.contains(answer)) {
                    val idx = answersIdxList.indexOf(it.toString())
                    resultAnswers.add(question.answers.getOrNull(idx))
                }
            }
            if (CollectionUtils.isEqualCollection(question.correctAnswers, resultAnswers.filterNotNull())) {
                scores++
            }
        }
        return scores
    }

}

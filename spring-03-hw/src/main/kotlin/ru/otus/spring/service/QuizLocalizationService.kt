package ru.otus.spring.service

import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import ru.otus.spring.service.config.ApplicationConfig


@Service
class QuizLocalizationService(
    private val messageSource: MessageSource,
    private val applicationConfig: ApplicationConfig,
) {
    fun introduction(): String = messageSource.getMessage("introduction", arrayOf(), applicationConfig.locale)

    fun greet(name: String): String = messageSource.getMessage("greeting", arrayOf(name), applicationConfig.locale)

    fun getResultMessage(resultScore: Int, questionsQuantity: Int, passingScore: Int): String =
        messageSource.getMessage(
            "scoreMessage",
            arrayOf(resultScore, questionsQuantity, passingScore),
            applicationConfig.locale
        )

    fun getQuestions() =
        messageSource.getMessage("questions", arrayOf(), applicationConfig.locale)

    fun getAnswers() =
        messageSource.getMessage("answers", arrayOf(), applicationConfig.locale)

    fun getWinnerResultMessage(): String =
        messageSource.getMessage("winnerFarewell", arrayOf(), applicationConfig.locale)

    fun getLooserResultMessage(): String =
        messageSource.getMessage("looserFarewell", arrayOf(), applicationConfig.locale)

}

package util

import ru.otus.spring.domain.Question

object Consts {
    const val FULL_QUESTION = "What is the default scope of the Spring Bean?, singleton,prototype,request,session;"
    const val QUESTION = "What is the default scope of the Spring Bean?"
    const val ANSWER_1 = "singleton"
    const val ANSWER_2 = "prototype"
    const val ANSWER_3 = "request"
    const val ANSWER_4 = "session"
    const val RIGHT_ANSWER = "singleton"

    fun getQuestionEntity() = Question(
        id = 1,
        question = QUESTION,
        answers = listOf(ANSWER_1, ANSWER_2, ANSWER_3, ANSWER_4),
        correctAnswers = mutableListOf(RIGHT_ANSWER)
    )
}
package ru.otus.spring.ui

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import ru.otus.spring.service.QuestionOutputDispatcher


@ShellComponent
class CommandController(
    private val questionExecutorImpl: QuestionOutputDispatcher,
) {

    @ShellMethod(key = ["start"])
    fun start() = questionExecutorImpl.introduce()

    @ShellMethod(key = ["test-spring"])
    fun beginTest() = questionExecutorImpl.beginTest()

    @ShellMethod(key = ["last-score"])
    fun score() {
        questionExecutorImpl.countScore()
    }

    @ShellMethod(key = ["last-answers"])
    fun getLastAnswers() {
        questionExecutorImpl.getAnswers()
    }

}

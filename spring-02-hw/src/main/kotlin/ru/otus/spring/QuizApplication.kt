package ru.otus.spring

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import ru.otus.spring.service.executor.QuestionExecutorImpl


@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
class QuizApplication

    fun main() {
        val ctx = AnnotationConfigApplicationContext(QuizApplication::class.java)
        val questionExecutorImpl = ctx.getBean(QuestionExecutorImpl::class.java)
        questionExecutorImpl.executeQuiz()
    }


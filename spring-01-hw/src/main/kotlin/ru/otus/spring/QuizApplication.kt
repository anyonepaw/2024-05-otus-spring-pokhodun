package ru.otus.spring

import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.spring.service.QuestionServiceImpl

class QuizApplication

fun main() {
    val ctx = ClassPathXmlApplicationContext("spring-context.xml")
    val questionServiceImpl = ctx.getBean(QuestionServiceImpl::class.java)
    questionServiceImpl.execute()
}

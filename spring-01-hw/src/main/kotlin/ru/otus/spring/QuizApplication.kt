package ru.otus.spring

import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.spring.service.PersonService

class QuizApplication

fun main(args: Array<String>) {
    val ctx = ClassPathXmlApplicationContext("spring-context.xml")
    val service: PersonService = ctx.getBean(PersonService::class.java)
    println(service.getSource())
}




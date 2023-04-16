package ru.otus.spring

import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.spring.domain.Person
import ru.otus.spring.service.PersonServiceImpl


class QuizApplication

    fun main() {
        val ctx = ClassPathXmlApplicationContext("spring-context.xml")
        val personService = ctx.getBean(PersonServiceImpl::class.java)

        val person: Person = personService.getByName("Ivan")
        println("name: " + person.name + " age:" + person.age)

    }

package ru.otus.spring.service

import ru.otus.spring.domain.Person

interface PersonService {
    fun getByName(name: String): Person
}

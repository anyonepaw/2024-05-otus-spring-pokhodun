package ru.otus.spring.dao

import ru.otus.spring.domain.Person

interface PersonDao {
    fun findByName(name: String): Person
}

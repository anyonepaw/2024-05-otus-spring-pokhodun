package ru.otus.spring.dao

import ru.otus.spring.domain.Person

class PersonDaoSimple : PersonDao {

    override fun findByName(name: String): Person {
        return Person(name, 18)
    }

}

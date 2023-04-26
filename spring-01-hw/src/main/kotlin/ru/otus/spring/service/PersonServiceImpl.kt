package ru.otus.spring.service

import ru.otus.spring.dao.PersonDao
import ru.otus.spring.domain.Person

class PersonServiceImpl(
    private val dao: PersonDao,
) : PersonService {

    override fun getByName(name: String): Person {
        return dao.findByName(name)
    }

}

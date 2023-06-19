package ru.otus.spring.service

import org.springframework.stereotype.Service
import ru.otus.spring.dao.PersonDao
import ru.otus.spring.domain.Person

@Service
class PersonServiceImpl(
    private val dao: PersonDao,
) : PersonService {

    override fun getByName(name: String): Person {
        return dao.findByName(name)
    }

}

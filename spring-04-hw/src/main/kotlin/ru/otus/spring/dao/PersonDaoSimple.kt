package ru.otus.spring.dao

import org.springframework.stereotype.Component
import ru.otus.spring.domain.Person

@Component
class PersonDaoSimple : PersonDao {

    override fun findByName(name: String): Person {
        return Person(name, 18)
    }

}

package ru.otus.spring.dao

import org.springframework.stereotype.Repository
import ru.otus.spring.domain.Person

@Repository
class PersonDaoSimple : PersonDao {

    override fun findByName(name: String): Person {
        return Person(name, 18)
    }

}

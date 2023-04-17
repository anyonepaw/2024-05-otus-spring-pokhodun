package ru.otus.spring.service

import java.nio.file.Path
import java.util.*
import ru.otus.spring.dao.PersonDao
import ru.otus.spring.domain.Person

class PersonServiceImpl(
    private val dao: PersonDao,
) : PersonService {

    private var resource: String? = null
    fun setResource(resource: String?) {
        this.resource = resource
    }

    override fun getByName(name: String): Person {
        return dao.findByName(name)
    }

    override fun getSource(): String? {

       return resource?.let { Scanner(Path.of(it)).useDelimiter("\\Z").next() }
    }
}

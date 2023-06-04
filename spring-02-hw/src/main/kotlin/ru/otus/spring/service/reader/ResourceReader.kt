package ru.otus.spring.service.reader

import org.springframework.core.io.Resource

interface ResourceReader {
    fun readResource(resource: Resource): List<String>
}

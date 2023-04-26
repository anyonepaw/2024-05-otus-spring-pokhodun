package ru.otus.spring.service.reader

import org.springframework.core.io.Resource

interface ReaderService {
    fun read(resource: Resource): List<String>
}

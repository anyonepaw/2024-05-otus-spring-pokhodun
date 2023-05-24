package ru.otus.spring.service.reader

interface ReaderService {
    fun read(resource: Any? = null): Collection<*>

}

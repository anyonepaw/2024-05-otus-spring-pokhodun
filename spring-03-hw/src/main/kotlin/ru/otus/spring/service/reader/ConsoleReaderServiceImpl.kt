package ru.otus.spring.service.reader

import java.util.*
import org.springframework.stereotype.Service

@Service
class ConsoleReaderServiceImpl : ReaderService {

    private val reader: Scanner = Scanner(System.`in`)

    override fun read(resource: Any?): Collection<Any> =
        reader.nextLine().toList()

}

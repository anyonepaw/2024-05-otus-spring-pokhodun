package ru.otus.spring.service.writer

import org.springframework.stereotype.Service

@Service
class ConsoleWriterServiceImpl : WriterService {
    override fun write(output: String) = print(output)
}

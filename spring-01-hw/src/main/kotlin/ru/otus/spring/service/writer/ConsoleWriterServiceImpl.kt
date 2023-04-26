package ru.otus.spring.service.writer

class ConsoleWriterServiceImpl : WriterService {
    override fun write(output: String) {
        print(output)
    }

}

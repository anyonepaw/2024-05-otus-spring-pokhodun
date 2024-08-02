package ru.otus.spring.service.reader

import org.springframework.stereotype.Service

@Service
class ResourceBundleReaderServiceImpl : ReaderService {
    override fun read(resource: Any?): Collection<*> {
        return listOf(resource as String)
    }
}

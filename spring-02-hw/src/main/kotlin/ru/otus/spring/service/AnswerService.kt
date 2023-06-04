package ru.otus.spring.service

interface AnswerService {
    fun getAnswers(): Map<String, List<String>>
}

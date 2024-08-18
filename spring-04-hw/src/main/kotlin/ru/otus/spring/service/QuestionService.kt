package ru.otus.spring.service

import ru.otus.spring.domain.Question

interface QuestionService {
    fun getQuestions(): List<Question>
}

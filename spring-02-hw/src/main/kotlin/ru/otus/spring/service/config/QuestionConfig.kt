package ru.otus.spring.service.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class QuestionConfig(
    @Value("\${questions.resource.path}")
    val questionsResource: Resource,
    @Value("\${answers.resource.path}")
    val answersResource: Resource,
    @Value("\${scores}")
    val scores: Int,
    @Value("\${answers.idx}")
    val answersIdx: String,
)

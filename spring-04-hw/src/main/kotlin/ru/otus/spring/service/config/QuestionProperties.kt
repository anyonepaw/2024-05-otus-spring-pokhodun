package ru.otus.spring.service.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "quiz")
data class QuestionProperties(
    var idx: String? = null,
    var score: Int = 0,
)

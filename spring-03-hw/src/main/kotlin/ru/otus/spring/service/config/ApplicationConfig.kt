package ru.otus.spring.service.config

import java.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties(
    QuestionProperties::class,
)
class ApplicationConfig(
    @Value("\${localization.locale}")
    val locale: Locale,
)

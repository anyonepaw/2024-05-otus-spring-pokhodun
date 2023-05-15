package ru.otus.spring.service.converter

interface QuestionConverter {

    fun convertToEntity(list: List<String>): List<Any>

    fun convertEntityToString(entities: List<Any>): String
}

package ru.otus.spring.service.converter

interface Converter {

    fun convertToEntity(list: List<String>): List<Any>

    fun convertEntityToString(entities: List<*>): String

}

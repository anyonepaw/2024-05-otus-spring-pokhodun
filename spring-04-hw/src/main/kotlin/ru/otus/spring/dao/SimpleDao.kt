package ru.otus.spring.dao

interface SimpleDao<K, V> {
    fun save(key: K, value: V)

    fun get(): Map<K, V>

    fun clear()
}
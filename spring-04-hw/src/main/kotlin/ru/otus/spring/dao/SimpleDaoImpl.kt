package ru.otus.spring.dao

import org.springframework.stereotype.Component

@Component
class SimpleDaoImpl<K, V> : SimpleDao<K, V> {

    private val map: MutableMap<K, V> = mutableMapOf()

    override fun save(key: K, value: V) {
        map[key] = value
    }

    override fun get(): Map<K, V> = map

    override fun clear() {
        map.clear()
    }

}
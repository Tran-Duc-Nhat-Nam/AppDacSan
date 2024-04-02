package com.example.appcsn.core.domain

interface Paginator<Key, Item> {
    suspend fun loadNext()
    fun reset()
}

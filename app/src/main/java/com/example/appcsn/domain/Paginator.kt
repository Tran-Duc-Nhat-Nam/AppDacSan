package com.example.appcsn.domain

interface Paginator<Key, Item> {
    suspend fun loadNext()
    fun reset()
}

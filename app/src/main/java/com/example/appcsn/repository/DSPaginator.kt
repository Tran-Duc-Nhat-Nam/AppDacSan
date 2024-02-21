package com.example.appcsn.repository

interface DSPaginator<Key, Item> {
    suspend fun loadNext()
    fun reset()
}

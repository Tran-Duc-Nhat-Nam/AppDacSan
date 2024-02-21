package com.example.appcsn.repository

class BaseRepository<Key, Item>(
    private val initKey: Key,
    private inline val onLoading: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit,
) : DSPaginator<Key, Item> {

    private var currentKey = initKey
    private var isRequest = false
    override suspend fun loadNext() {
        if (isRequest) {
            return
        }
        isRequest = true
        onLoading(true)
        val kq = onRequest(currentKey)
        isRequest = false
        val ds = kq.getOrElse {
            onError(it)
            onLoading(false)
            return
        }
        currentKey = getNextKey(ds)
        onSuccess(ds, currentKey)
    }

    override fun reset() {
        currentKey = initKey
    }

}

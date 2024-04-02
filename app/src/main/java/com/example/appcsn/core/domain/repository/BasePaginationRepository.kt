package com.example.appcsn.core.domain.repository

import com.example.appcsn.core.domain.Paginator

class BasePaginationRepository<Key, Item>(
    private val initKey: Key, // Key để lấy danh sách item đầu tiên từ API
    private inline val onLoading: (Boolean) -> Unit, // Hàm để cung cấp tình trạng load danh sách item
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>, // Hàm để yêu cầu dữ liệu từ key
    private inline val getNextKey: suspend (List<Item>) -> Key, // Hàm đễ lấy key cho danh sách item kế tiếp từ danh sách item truyền vào
    private inline val onError: suspend (Throwable?) -> Unit, // Hàm nhận lỗi từ API khi quá trình lấy dữ liệu từ API thất bại
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit,  // Hàm nhận danh sách item và key tiếp theo chạy khi quá trình lấy dữ liệu từ API thành công
) : Paginator<Key, Item> {
    private var currentKey = initKey // Biến chứa key hiện tại
    private var isRequest = false // Biến chứa giá trị có đang yêu cầu hay không

    // Hàm load danh sách item kế tiếp từ API bằng key hiện tại
    override suspend fun loadNext() {
        // Nếu đang yêu cầu thì không chạy
        if (isRequest) {
            return
        }
        // Thay đổi giá trị biến isRequest để cho biết là đang yêu cầu
        isRequest = true
        // Truyền giá trị true vào hàm để cho biết đang load dữ liệu
        onLoading(true)
        // Yêu cầu dữ liệu từ API bằng key hiện tại và lưu kết quả từ API vào biến kq
        val kq = onRequest(currentKey)
        // Thay đổi giá trị biến isRequest để cho biết là đang không có yêu cầu
        isRequest = false
        // Nếu đọc API thành công thì lấy danh sách item từ kq gán cho biến ds
        val ds = kq.getOrElse {
            // Nếu thất bại thì chạy hàm onError và truyền lỗi do API phản hồi
            onError(it)
            // Truyền giá trị false vào hàm để cho biết đang không load dữ liệu
            onLoading(false)
            return
        }
        // Gán key tiếp theo cho biến currentKey
        currentKey = getNextKey(ds)
        // Chạy hàm onSuccess và chuyền ds cùng key hiện tại vào
        onSuccess(ds, currentKey)
        // Truyền giá trị false vào hàm để cho biết đang không load dữ liệu
        onLoading(false)
    }

    // Hàm reset key hiện tại thành yêu ban đầu (từ đó hàm loadNext sẽ bắt đầu lại từ đầu)
    override fun reset() {
        currentKey = initKey
    }
}

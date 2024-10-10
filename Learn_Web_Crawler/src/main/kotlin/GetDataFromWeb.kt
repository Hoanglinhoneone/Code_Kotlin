import org.jsoup.Jsoup

fun main() {
    // URL của trang web mà bạn muốn lấy dữ liệu
    val url = "https://mina.mazii.net/#/alphabet" // Thay thế bằng URL thực tế

    try {
        // Kết nối đến trang web và lấy tài liệu
        val document = Jsoup.connect(url).get()

        // Lấy tất cả các thẻ div với class cụ thể
        val kanaItems = document.select("div.kana-item-alphabet.alphabet-content-item30")

        // Lặp qua các thẻ đã chọn
        for (item in kanaItems) {
            // Lấy nội dung của thẻ main (nếu có)
            val mainText = item.select("div.main").text()
            // Lấy nội dung của thẻ romanji (nếu có)
            val romanjiText = item.select("div.romanji").text()

            println("Hiragana: $mainText, Romaji: $romanjiText")
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

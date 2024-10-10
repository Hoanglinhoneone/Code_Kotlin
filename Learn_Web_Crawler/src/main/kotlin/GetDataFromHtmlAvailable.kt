import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

fun main() {
    // Chuỗi HTML mà bạn muốn phân tích
    val htmlString = """
        <div class="kana-item-alphabet alphabet-content-item30">
            <div class="center-alphabet">
                <div class="main">ほ</div>
                <div class="romanji">ho</div>
            </div>
        </div>
    """.trimIndent()

    // Phân tích chuỗi HTML
    val document: Document = Jsoup.parse(htmlString)

    // Lấy tất cả các thẻ div với class cụ thể
    val kanaItems: List<Element> = document.select("div.kana-item-alphabet.alphabet-content-item30")

    // Lặp qua các thẻ đã chọn
    for (item in kanaItems) {
        // Lấy nội dung của thẻ main (nếu có)
        val mainText = item.select("div.main").text()
        // Lấy nội dung của thẻ romanji (nếu có)
        val romanjiText = item.select("div.romanji").text()

        println("Hiragana: $mainText, Romaji: $romanjiText")
    }
}

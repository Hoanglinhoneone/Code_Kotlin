import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun main() {
    // URL của trang web bạn muốn lấy HTML
    val url = "https://mina.mazii.net/#/alphabet"

    // Gửi yêu cầu HTTP GET để lấy HTML của trang
    val doc: Document = Jsoup.connect(url).get()

    // In ra HTML của trang
    println(doc.html())
}
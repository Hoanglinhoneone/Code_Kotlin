import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

fun main() {
    // URL bắt đầu của crawler
    val startUrl = "https://mina.mazii.net/#/alphabet"

    // Gửi yêu cầu đến URL và lấy HTML
    val doc: Document = Jsoup.connect(startUrl).get()

    // In tiêu đề trang
    println("Title: ${doc.title()}")

    // Tìm tất cả các liên kết trên trang
    val links = doc.select("a[href]")
    val texts = doc.select("p")

    // In tất cả các URL liên kết
    for (link: Element in links) {
        println("Link: ${link.attr("href")} - Text: ${link.text()}")
    }
}
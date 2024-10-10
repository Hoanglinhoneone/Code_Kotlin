import org.jsoup.Jsoup

fun main() {
    val url = "https://mina.mazii.net/#/alphabet"

    try {
        val document = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
            .get()

        val mainDiv = document.select("*")

        for (div in mainDiv) {
            println(div.text())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

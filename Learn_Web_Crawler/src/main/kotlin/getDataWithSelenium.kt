import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebElement
import kotlin.system.exitProcess

fun main() {
    // Đường dẫn đến ChromeDriver
    System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe")

    // Tùy chọn cho Chrome
    val options = ChromeOptions()
    options.addArguments("--headless") // Ch khạy trong chế độ kông có gd
    val driver: WebDriver = ChromeDriver(options)

    try {
        // Mở trang web
        driver.get("https://mina.mazii.net/#/alphabet")

        // Lấy tất cả các thẻ div với class cần tìm vd: item30
        val kanaItems: List<WebElement> = driver.findElements(By.cssSelector("div.kana-item-alphabet.alphabet-content-item30"))

        // Lặp qua các thẻ đã chọn
        for (item in kanaItems) {
            // Lấy nội dung của thẻ main (nếu có)
            val mainText = item.findElement(By.cssSelector("div.main")).text
            // Lấy nội dung của thẻ romanji (nếu có)
            val romanjiText = item.findElement(By.cssSelector("div.romanji")).text

            println("Hiragana: $mainText, Romaji: $romanjiText")
        }

    } catch (e: Exception) {
        println("erorr -------------")
        e.printStackTrace()
    } finally {
        // Đóng trình duyệt
        driver.quit()
        exitProcess(0)
    }
}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

fun main() {
    // Đặt đường dẫn cho ChromeDriver
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver")

    // Khởi tạo ChromeDriver
    val driver: WebDriver = ChromeDriver()

    // Mở trang web
    driver.get("https://mina.mazii.net/#/alphabet")

    // Lấy HTML sau khi trang đã tải đầy đủ
    val htmlContent = driver.pageSource

    // In ra HTML của trang
    println(htmlContent)

    // Đóng trình duyệt
    driver.quit()
}
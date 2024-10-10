import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

fun main() {
    System.setProperty("webdriver.chrome.driver", "path/to/chromedriver")

    val options = ChromeOptions()
    options.addArguments("--headless") // Chạy ẩn nếu cần
    val driver: WebDriver = ChromeDriver(options)

    driver.get("https://mina.mazii.net/#/alphabet")

    Thread.sleep(3000)

    val mainDiv = driver.findElement(By.cssSelector("div.main"))
    println(mainDiv.text)

    driver.quit()
}

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

object SeleniumTests {
    @JvmStatic
    fun main(args: Array<String>) {
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe")

            // Tùy chọn cho Chrome
//            val options = ChromeOptions()
            // Đường dẫn tới Chrome
//            options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe")

            // Khởi tạo WebDriver với các tùy chọn
            val driver: WebDriver = ChromeDriver()

            // Điều hướng tới trang web
            driver["https://mina.mazii.net/#/alphabet"]
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500))
//        item-header tabalphabet1 item-header-active
//            driver.findElement(By.cssSelector(By.xpath(By.xpath("//*[contains(@class, 'class1') and contains(@class, 'class2')]"));
//            // Chờ trang web tải dữ liệu (nếu cần, có thể dùng WebDriverWait nếu dữ liệu tải bất đồng bộ)
////            Thread.sleep(2000)
//
//            // Lấy tất cả các thẻ div với class "main" chứa Hiragana
//            // Tìm các thẻ main
//            val kanaMainItems: List<WebElement> =
////                driver.findElements(By.xpath("//div[@class='main']"))
//                driver.findElements(By.className("main"))
//            // val kanaRomajiItems: List<WebElement> =
//            // driver.findElements(By.xpath("//div[@class='romaji']")) // Tìm các thẻ romaji
//            // Lặp qua các thẻ đã chọn
//            if(kanaMainItems.isEmpty()) {
//                println("Chưa lấy được data")
//            }
//            else {
//                var n = 0;
//                for (item in kanaMainItems) {
//                    // Lấy nội dung của thẻ main (nếu có)
//                    val mainText = item.text
//                    println("Hiragana: $mainText")
//                    n++
//                }
//                println(n)
//            }
//
//            // Đóng trình duyệt
//        driver.quit()
    }
}

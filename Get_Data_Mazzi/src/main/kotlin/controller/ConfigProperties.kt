package controller

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.TimeUnit

class ConfigProperties {
    private val filePaths = "config.properties"

    private fun createFile() {
        val propertiesFile = File(this.filePaths)

        // Kiểm tra xem file đã tồn tại chưa
        if (!propertiesFile.exists()) {
            // Tạo file properties mới và thêm nội dung
            val properties = Properties().apply {
                setProperty("n", "1")  // Thay đổi giá trị theo nhu cầu
            }

            // Ghi nội dung vào file
            FileOutputStream(propertiesFile).use { outputStream ->
                properties.store(outputStream, "Configuration Properties")
            }

            println("File config.properties đã được tạo.")
        } else {
            println("File config.properties đã tồn tại.")
        }

        // Kiểm tra sự tồn tại của file sau khi chạy
        println("Current working directory: ${System.getProperty("user.dir")}")
        println("File exists: ${propertiesFile.exists()}")
    }

    fun readFile(): Long {
        createFile()
        // read file properties
        val properties = Properties()
        FileInputStream(this.filePaths).use { inputStream ->
            properties.load(inputStream)
        }

        // get number time :
        val n = properties.getProperty("n")?.toInt() ?: 1
        return TimeUnit.HOURS.toMillis(n.toLong())
    }
}
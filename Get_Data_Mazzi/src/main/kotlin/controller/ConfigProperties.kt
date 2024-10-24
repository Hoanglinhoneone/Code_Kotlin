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

        if (!propertiesFile.exists()) {
            val properties = Properties().apply {
                setProperty("n", "1")
            }

            FileOutputStream(propertiesFile).use { outputStream ->
                properties.store(outputStream, "Configuration Properties")
            }

            println("File config.properties đã được tạo.")
        } else {
            println("File config.properties đã tồn tại.")
        }

        println("Current working directory: ${System.getProperty("user.dir")}")
        println("File exists: ${propertiesFile.exists()}")
    }

    fun readFile(): Long {
        createFile()
        val properties = Properties()
        FileInputStream(this.filePaths).use { inputStream ->
            properties.load(inputStream)
        }

        val n = properties.getProperty("n")?.toInt() ?: 1
        return TimeUnit.HOURS.toMillis(n.toLong())
    }
}
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONArray
import java.io.File
import java.io.FileReader
import java.io.FileWriter

fun showJsonArray(jsonArray: JSONArray) {
    for (i in 0 until jsonArray.length()){
        println(jsonArray.getJSONObject(i))
    }
}

fun saveJsonArraytoFile(filePaths: String, jsonArray: JSONArray) {
    val gson = Gson()
    val jsonString = gson.toJson(jsonArray)
    println(jsonString)

    val file = File(filePaths)

    try {
        // create file if not found
        if(!file.exists()) {
            file.createNewFile();
        }

        // write jsonString to file
        val  fileWriter = FileWriter(file)
        fileWriter.use {writer ->
            writer.write(jsonString)
        }
        println("JSON data has been saved to ${file.absolutePath}")
    } catch (e : Exception) {
        println("Error")
        e.printStackTrace()
    }
}

fun saveJsonArraytoFile(filePaths: String, jsonString: String) {
    val file = File(filePaths)
    try {
        // create file if not found
        if(!file.exists()) {
            file.createNewFile();
        }

        // write jsonString to file
        val  fileWriter = FileWriter(file)
        fileWriter.use {writer ->
            writer.write(jsonString)
        }
        println("JSON data has been saved to ${file.absolutePath}")
    } catch (e : Exception) {
        println("Error")
        e.printStackTrace()
    }
}

fun addAttribute() {
    val gson = Gson()

    // Đường dẫn tới file JSON
    val filePath = "path/to/your/data.json"
    val file = FileReader(filePath)

    // Đọc nội dung JSON từ file
    val jsonObject = JsonParser.parseReader(file).asJsonObject
    file.close()

    // Lấy danh sách người dùng từ JSON
    val usersArray = jsonObject.getAsJsonArray("users")

    // Duyệt qua tất cả các người dùng và thêm thuộc tính 'age'
    for (userElement in usersArray) {
        val userObject = userElement.asJsonObject

        // Thêm thuộc tính 'age' với giá trị mặc định
        userObject.addProperty("age", 25)  // Ví dụ: Thêm tuổi mặc định là 25 cho tất cả
    }

    // Ghi lại nội dung JSON sau khi đã chỉnh sửa vào file
    val writer = FileWriter(filePath)
    gson.toJson(jsonObject, writer)
    writer.flush()
    writer.close()

    println("Thêm thuộc tính 'age' thành công cho tất cả các đối tượng!")
}


fun main(){
    val fileName = "DataJson"
    val fileName2 = "DataJson2"
    val METHOD = "GET"
    val apiLink : String = "https://mina.mazii.net/api/getKana.php"


    val url = URL(apiLink)
    val connection = url.openConnection() as HttpURLConnection

    try {
        connection.requestMethod = METHOD
        connection.connect()

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            var japanese =  connection.inputStream.bufferedReader().use { it.readText() }
            println(japanese)
            val jsonArray = JSONArray(japanese)
//            saveJsonArraytoFile(fileName, jsonArray)
//            saveJsonArraytoFile(fileName2, japanese)
            showJsonArray(jsonArray)
//            println("ResponCode: $japanese")
        } else {
            println("ERROR: $responseCode")
        }
    } catch (e : Exception) {
        e.printStackTrace()
    } finally {
        connection.disconnect()
    }
}
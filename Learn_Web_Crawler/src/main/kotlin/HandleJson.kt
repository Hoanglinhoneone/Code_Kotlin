import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForField.Write
import org.json.JSONArray
import java.io.File
import java.io.FileReader
import java.io.FileWriter

fun saveFile(filePath : String, jsonString: String): String {
    val file = File(filePath)
    var fileRoot = ""
    try {
        // create if file not created
        if(!file.exists()) {
            file.createNewFile()
        }
        // write jsonString to file
        val fileWriter = FileWriter(file)
        fileWriter.use { Writer ->
            Writer.write(jsonString)
        }
        println("data saved to ${file.absolutePath}")
        fileRoot = file.absolutePath.toString()
    } catch (e : Exception) {
        e.printStackTrace()
    }
    return fileRoot
}

fun getDataFromApi() : String {
    // set up
    var jsonString = ""
    val METHOD = "GET"
    val apiLink : String = "https://mina.mazii.net/api/getKana.php"
    val url = URL(apiLink)
    val connection = url.openConnection() as HttpURLConnection

    try {
        // call api
        connection.requestMethod = METHOD
        connection.connect()

        // save code
        val responseCode = connection.responseCode

        // check if code = 200 =>> request success
        if(responseCode == HttpURLConnection.HTTP_OK) {
            jsonString = connection.inputStream.bufferedReader().use { it.readText() }
//            println(jsonString)

        } else {
            println("ERROR : $responseCode")
        }
    } catch (e : Exception) {
        e.printStackTrace()
    }
    return jsonString
}

fun getDataFromFile(filePath: String) : JsonArray {
    val gson = Gson()

    val file = FileReader(filePath)

    // read content of file
    val jsonArray = JsonParser.parseReader(file).asJsonArray
    file.close()
    return jsonArray
}

fun outPut1(filePath: String) {
    // get ds json
    val jsonArray : JsonArray =  getDataFromFile(filePath)
    var i = 0;
    for (jsonElement in jsonArray) {
        val jsonObject = jsonElement.asJsonObject
        jsonObject.addProperty("vidu", i)
        // Truy cập các thuộc tính của từng đối tượng
        val id = jsonObject.get("id").asString
        val romaji = jsonObject.get("romaji").asString
        val hira = jsonObject.get("hira").asString
        val kata = jsonObject.get("kata").asString
        val groupe = jsonObject.get("groupe").asString
        val example = jsonObject.get("example").asString
        val vidu = jsonObject.get("vidu").asString
        jsonObject.addProperty("vidu", i)
        i++
        // In ra thông tin từng đối tượng
        println("ID: $id, Romaji: $romaji, Hira: $hira, Kata: $kata, Groupe: $groupe, Example: $example. vidu: $vidu")
    }
    // Ghi lại JSON sau khi thay đổi
    val writer = FileWriter(filePath)
    writer.write(jsonArray.toString())
    writer.flush()
    writer.close()

}
fun outPut2(filePath: String) {
    // get ds json
    val jsonArray : JsonArray =  getDataFromFile(filePath)
    for (jsonElement in jsonArray) {
        val jsonObject = jsonElement.asJsonObject
        jsonObject.remove("example")
    }
    // Ghi lại JSON sau khi thay đổi
    val writer = FileWriter(filePath)
    writer.write(jsonArray.toString())
    writer.flush()
    writer.close()

    //check
    val jsonArray2 : JsonArray =  getDataFromFile(filePath)
    for (i in 0 until jsonArray2.size()){
        println(jsonArray.get(i))
    }
}
fun outPut3(filePath: String) {
    // get ds json
    val jsonArray : JsonArray =  getDataFromFile(filePath)
    for (jsonElement in jsonArray) {
        val jsonObject = jsonElement.asJsonObject
        jsonObject.addProperty("romaji", "a")
    }
    // Ghi lại JSON sau khi thay đổi
    val writer = FileWriter(filePath)
    writer.write(jsonArray.toString())
    writer.flush()
    writer.close()

    //check
    val jsonArray2 : JsonArray =  getDataFromFile(filePath)
    for (i in 0 until jsonArray2.size()){
        println(jsonArray.get(i))
    }
}
fun outPut4(filePath: String) {
    // get ds json
    val jsonArray : JsonArray =  getDataFromFile(filePath)
    var i = 1;
    var j = 1
    for (jsonElement in jsonArray) {
        val jsonObject = jsonElement.asJsonObject
        jsonObject.addProperty("row", "$j")
        jsonObject.addProperty("colum", "$i")

        if(i == 5) {
            i = 1
            j++
        }
        i++
    }
    // Ghi lại JSON sau khi thay đổi
    val writer = FileWriter(filePath)
    writer.write(jsonArray.toString())
    writer.flush()
    writer.close()

    //check
    val jsonArray2 : JsonArray =  getDataFromFile(filePath)
    for (i in 0 until jsonArray2.size()){
        println(jsonArray.get(i))
    }
}
fun main() {
    // input

    // get data from api
    val jsonString : String = getDataFromApi()

    //save data to file and save filepath of file
    val fileName1 = "DataJson1"
    val fileName2 = "DataJson2"
    val fileName3 = "DataJson3"
    val fileName4 = "DataJson4"

//    val fileRoot1 = saveFile(fileName1, jsonString)
//    val fileRoot2 = saveFile(fileName2, jsonString)
//    val fileRoot3 = saveFile(fileName3, jsonString)
    val fileRoot4 = saveFile(fileName4, jsonString)

//    // output1 : add them thuoc tinh vi du
//    outPut1(fileRoot1)

//    //output2 : xoa di thuoc tinh example
//    outPut2(fileRoot2)

////    output3 : sua thuoc tinh romaji ve full a
//    outPut3(fileRoot3)

    //output4 : them thuoc tinh vi tri colum and row tren ma tran
    outPut4(fileRoot4)

}
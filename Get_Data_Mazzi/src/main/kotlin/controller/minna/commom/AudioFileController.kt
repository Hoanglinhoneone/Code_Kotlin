package controller.minna.commom

import database.DatabaseHelper
import database.minna.AudioFileDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.commom.AudioFile
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class AudioFileController() {

    private var FirstGetAudioFile = true
    private lateinit var retrofitMinna: RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }

    fun getAudioFile(type: String) {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1..50) {
                retrofitMinna.getAudioFile(i, type) { audioFileList ->
                    if (audioFileList != null) {

                        for (audioFile in audioFileList) {
                            println(audioFile.toString())
                        }

                        // first get => insert
                        if (FirstGetAudioFile) {
                            for (audioFile in audioFileList) {
                                if (connection != null) {
                                    AudioFileDatabase(connection, audioFile).insert()
                                }
                            }
                            FirstGetAudioFile = false

                        } else {
                            // check update if data change
                            for (audioFileNew in audioFileList) {
                                val audioFileDatabase = connection?.let {
                                    AudioFileDatabase(it, audioFileNew)
                                }
                                val audioFileOld = audioFileDatabase?.select(audioFileNew.id.toInt())

                                if (audioFileOld != null) {
                                    checkUpdateData(audioFileNew, audioFileOld, connection)
                                } else {
                                    audioFileDatabase?.insert()
                                }
                            }
                        }
                    } else {
                        // Xử lý trường hợp lỗi
                        println("list is null")
                    }
                    connection?.close()
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun checkUpdateData(audioFileNew: AudioFile, audioFileOld: AudioFile, connection: Connection) {

        if (audioFileNew.toString().compareTo(audioFileOld.toString()) == 0) return

        var dataChange = false

        val alphabetNewStr = audioFileNew.getString()
        val alphabetOldStr = audioFileOld.getString()
        val columnNewStr = alphabetNewStr.split("/")
        val columnOldStr = alphabetOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "lesson_id"
                    2 -> "indx"
                    3 -> "typee"
                    4 -> "link"
                    else -> "status"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, audioFileNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            AudioFileDatabase(connection, audioFileNew).update(myMutableMap, audioFileNew.id.toInt())
            val tableChange = TableChange(audioFileNew.id.toInt(), "alphabet", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }
}
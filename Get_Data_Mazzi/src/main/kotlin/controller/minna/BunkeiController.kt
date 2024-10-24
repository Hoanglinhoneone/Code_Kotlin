package controller.minna

import database.DatabaseHelper
import database.minna.BunkeiDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.Bunkei
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class BunkeiController() {
    private var FirstGetAudioFile = true
    private lateinit var retrofitMinna : RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }
    fun getBunkei() {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1..50) {
                retrofitMinna.getBunkeiList(i) { bunkeiList ->
                    if (bunkeiList != null) {

                        for (bunkei in bunkeiList) {
                            println(bunkei.toString())
                        }

                        // first get => insert
                        if (FirstGetAudioFile) {
                            for (bunkei in bunkeiList) {
                                if (connection != null) {
                                     BunkeiDatabase(connection,bunkei ).insert()
                                }
                            }
                            FirstGetAudioFile = false

                        } else {
                            // check update if data change
                            for (bunkeiNew in bunkeiList) {
                                val bunkeiDatabase = connection?.let {
                                    BunkeiDatabase(it, bunkeiNew)
                                }
                                val bunkeiOld = bunkeiDatabase?.select(bunkeiNew.id.toInt())

                                if (bunkeiOld != null) {
                                    checkUpdateData(bunkeiNew, bunkeiOld, connection)
                                } else {
                                    bunkeiDatabase?.insert()
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

    private fun checkUpdateData(bunkeiNew: Bunkei, bunkeiOld: Bunkei, connection: Connection) {

        if (bunkeiNew.toString().compareTo(bunkeiOld.toString()) == 0) return

        var dataChange = false

        val bunkeiNewStr = bunkeiNew.getString()
        val bunkeiOldStr = bunkeiOld.getString()
        val columnNewStr = bunkeiNewStr.split("/")
        val columnOldStr = bunkeiOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "lesson_id"
                    2 -> "bunkei"
                    3 -> "vi_mean"
                    4 -> "roumaji"
                    else -> "favorite"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, bunkeiNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            BunkeiDatabase(connection, bunkeiNew).update(myMutableMap, bunkeiNew.id.toInt())
            val tableChange = TableChange(bunkeiNew.id.toInt(), "bunkei", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }
}
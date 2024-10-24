package controller.minna

import database.DatabaseHelper
import database.minna.KaiwaDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.Kaiwa
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class KaiwaController() {
    private var FirstGetKaiwa = true
    private lateinit var retrofitMinna : RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }
    

    fun getkaiwa() {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1 .. 50) {
                retrofitMinna.getKaiwaList(i) { kaiwaList ->
                    if (kaiwaList != null) {

                        for (kaiwa in kaiwaList) {
                            println(kaiwa.toString())
                        }

                        // first get => insert
                        if (FirstGetKaiwa) {
                            for (kaiwa in kaiwaList) {
                                if (connection != null) {
                                    KaiwaDatabase(connection, kaiwa).insert()
                                }
                            }
                            FirstGetKaiwa = false

                        } else {
                            // check update if data change
                            for (kaiwaNew in kaiwaList) {
                                val kaiwaDatabase = connection?.let {
                                    KaiwaDatabase(it, kaiwaNew)
                                }
                                val kaiwaOld = kaiwaDatabase?.select(kaiwaNew.id.toInt())

                                if (kaiwaOld != null) {
                                    checkUpdateData(kaiwaNew, kaiwaOld, connection)
                                } else {
                                    kaiwaDatabase?.insert()
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

    private fun checkUpdateData(kaiwaNew: Kaiwa, kaiwaOld: Kaiwa, connection: Connection) {

        if (kaiwaNew.toString().compareTo(kaiwaOld.toString()) == 0) return

        var dataChange = false

        val kaiwaNewStr = kaiwaNew.getString()
        val kaiwaOldStr = kaiwaOld.getString()
        val columnNewStr = kaiwaNewStr.split("/")
        val columnOldStr = kaiwaOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "lesson_id"
                    2 -> "characterr"
                    3 -> "kaiwa"
                    4 -> "vi_mean"
                    5 -> "c_roumaji"
                    6 -> "j_roumaji"
                    else -> "example"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, kaiwaNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            KaiwaDatabase(connection, kaiwaNew).update(myMutableMap, kaiwaNew.id.toInt())
            val tableChange = TableChange(kaiwaNew.id.toInt(), "kaiwa", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }
}
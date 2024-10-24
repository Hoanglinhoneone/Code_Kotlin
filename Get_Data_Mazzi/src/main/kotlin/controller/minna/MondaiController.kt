package controller.minna

import database.DatabaseHelper
import database.minna.MondaiDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.Mondai
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class MondaiController() {
    private var FirstGetMondai = true
    private lateinit var retrofitMinna : RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }

    fun getmondai() {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1 .. 50) {
                retrofitMinna.getMondaiList(i) { mondaiList ->
                    if (mondaiList != null) {

                        for (mondai in mondaiList) {
                            println(mondai.toString())
                        }

                        // first get => insert
                        if (FirstGetMondai) {
                            for (mondai in mondaiList) {
                                if (connection != null) {
                                    MondaiDatabase(connection, mondai).insert()
                                }
                            }
                            FirstGetMondai = false

                        } else {
                            // check update if data change
                            for (mondaiNew in mondaiList) {
                                val mondaiDatabase = connection?.let {
                                    MondaiDatabase(it, mondaiNew)
                                }
                                val mondaiOld = mondaiDatabase?.select(mondaiNew.id.toInt())

                                if (mondaiOld != null) {
                                    checkUpdateData(mondaiNew, mondaiOld, connection)
                                } else {
                                    mondaiDatabase?.insert()
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

    private fun checkUpdateData(mondaiNew: Mondai, mondaiOld: Mondai, connection: Connection) {

        if (mondaiNew.toString().compareTo(mondaiOld.toString()) == 0) return

        var dataChange = false

        val mondaiNewStr = mondaiNew.getString()
        val mondaiOldStr = mondaiOld.getString()
        val columnNewStr = mondaiNewStr.split("/")
        val columnOldStr = mondaiOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "lesson_id"
                    2 -> "namee"
                    3 -> "typee"
                    4 -> "question_num"
                    else -> "answer"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, mondaiNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            MondaiDatabase(connection, mondaiNew).update(myMutableMap, mondaiNew.id.toInt())
            val tableChange = TableChange(mondaiNew.id.toInt(), "mondai", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {
        // POST API
    }
}
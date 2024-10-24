package controller.minna

import database.DatabaseHelper
import database.minna.ReibunDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.Reibun
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class ReibunController() {
    private var FirstGetReibun = true
    private lateinit var retrofitMinna: RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }

    fun getreibun() {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1..50) {
                retrofitMinna.getReibunList(i) { reibunList ->
                    if (reibunList != null) {

                        for (reibun in reibunList) {
                            println(reibun.toString())
                        }

                        // first get => insert
                        if (FirstGetReibun) {
                            for (reibun in reibunList) {
                                if (connection != null) {
                                    ReibunDatabase(connection, reibun).insert()
                                }
                            }
                            FirstGetReibun = false

                        } else {
                            // check update if data change
                            for (reibunNew in reibunList) {
                                val reibunDatabase = connection?.let {
                                    ReibunDatabase(it, reibunNew)
                                }
                                val reibunOld = reibunDatabase?.select(reibunNew.id.toInt())

                                if (reibunOld != null) {
                                    checkUpdateData(reibunNew, reibunOld, connection)
                                } else {
                                    reibunDatabase?.insert()
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

    private fun checkUpdateData(reibunNew: Reibun, reibunOld: Reibun, connection: Connection) {

        if (reibunNew.toString().compareTo(reibunOld.toString()) == 0) return

        var dataChange = false

        val reibunNewStr = reibunNew.getString()
        val reibunOldStr = reibunOld.getString()
        val columnNewStr = reibunNewStr.split("/")
        val columnOldStr = reibunOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "lesson_id"
                    2 -> "typee"
                    3 -> "reibun"
                    4 -> "vi_mean"
                    else -> "j_roumaji"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, reibunNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            ReibunDatabase(connection, reibunNew).update(myMutableMap, reibunNew.id.toInt())
            val tableChange = TableChange(reibunNew.id.toInt(), "reibun", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }
}
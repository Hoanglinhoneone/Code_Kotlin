package controller.minna

import database.DatabaseHelper
import database.minna.ReferenceDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.Reference
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class ReferenceController() {
    private var FirstGetReference = true
    private lateinit var retrofitMinna : RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }

    fun getreference() {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1 .. 50) {
                retrofitMinna.getReferenceList(i) { referenceList ->
                    if (referenceList != null) {

                        for (reference in referenceList) {
                            println(reference.toString())
                        }

                        // first get => insert
                        if (FirstGetReference) {
                            for (reference in referenceList) {
                                if (connection != null) {
                                    ReferenceDatabase(connection, reference).insert()
                                }
                            }
                            FirstGetReference = false

                        } else {
                            // check update if data change
                            for (referenceNew in referenceList) {
                                val referenceDatabase = connection?.let {
                                    ReferenceDatabase(it, referenceNew)
                                }
                                val referenceOld = referenceDatabase?.select(referenceNew.id.toInt())

                                if (referenceOld != null) {
                                    checkUpdateData(referenceNew, referenceOld, connection)
                                } else {
                                    referenceDatabase?.insert()
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

    private fun checkUpdateData(referenceNew: Reference, referenceOld: Reference, connection: Connection) {

        if (referenceNew.toString().compareTo(referenceOld.toString()) == 0) return

        var dataChange = false

        val referenceNewStr = referenceNew.getString()
        val referenceOldStr = referenceOld.getString()
        val columnNewStr = referenceNewStr.split("/")
        val columnOldStr = referenceOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "lesson_id"
                    2 -> "japanese"
                    3 -> "roumaji"
                    4 -> "vietnamese"
                    else -> "note"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, referenceNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            ReferenceDatabase(connection, referenceNew).update(myMutableMap, referenceNew.id.toInt())
            val tableChange = TableChange(referenceNew.id.toInt(), "reference", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }
}
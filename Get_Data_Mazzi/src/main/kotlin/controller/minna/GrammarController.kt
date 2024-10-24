package controller.minna

import database.DatabaseHelper
import database.minna.GrammarDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.Grammar
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class GrammarController() {
    private var FirstGetGrammar = true
    private lateinit var retrofitMinna : RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }

    fun getgrammar() {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1 .. 50) {
                retrofitMinna.getGrammarList(i) { grammarList ->
                    if (grammarList != null) {

                        for (grammar in grammarList) {
                            println(grammar.toString())
                        }

                        // first get => insert
                        if (FirstGetGrammar) {
                            for (grammar in grammarList) {
                                if (connection != null) {
                                    GrammarDatabase(connection, grammar).insert()
                                }
                            }
                            FirstGetGrammar = false

                        } else {
                            // check update if data change
                            for (grammarNew in grammarList) {
                                val grammarDatabase = connection?.let {
                                    GrammarDatabase(it, grammarNew)
                                }
                                val grammarOld = grammarDatabase?.select(grammarNew.id.toInt())

                                if (grammarOld != null) {
                                    checkUpdateData(grammarNew, grammarOld, connection)
                                } else {
                                    grammarDatabase?.insert()
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

    private fun checkUpdateData(grammarNew: Grammar, grammarOld: Grammar, connection: Connection) {

        if (grammarNew.toString().compareTo(grammarOld.toString()) == 0) return

        var dataChange = false

        val grammarNewStr = grammarNew.getString()
        val grammarOldStr = grammarOld.getString()
        val columnNewStr = grammarNewStr.split("/")
        val columnOldStr = grammarOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "leasson_id"
                    2 -> "namee"
                    3 -> "uname"
                    4 -> "tag"
                    else -> "favorite"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, grammarNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            GrammarDatabase(connection, grammarNew).update(myMutableMap, grammarNew.id.toInt())
            val tableChange = TableChange(grammarNew.id.toInt(), "grammar", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }
}
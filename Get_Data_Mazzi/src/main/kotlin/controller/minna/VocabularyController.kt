package controller.minna

import database.DatabaseHelper
import database.minna.VocabularyDatabase
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import model.minna.Vocabulary
import request.retrofit.RetrofitMinna
import java.sql.Connection
import java.sql.SQLException

class VocabularyController() {
    private var FirstGetVocabulary = true
    private lateinit var retrofitMinna: RetrofitMinna

    constructor(retrofitMinna: RetrofitMinna) : this() {
        this.retrofitMinna = retrofitMinna
    }


    fun getvocabulary() {
        try {
            val connection = DatabaseHelper.connect()
            for (i in 1..50) {
                retrofitMinna.getVocabularyList(i) { vocabularyList ->
                    if (vocabularyList != null) {

                        for (vocabulary in vocabularyList) {
                            println(vocabulary.toString())
                        }

                        // first get => insert
                        if (FirstGetVocabulary) {
                            for (vocabulary in vocabularyList) {
                                if (connection != null) {
                                    VocabularyDatabase(connection, vocabulary).insert()
                                }
                            }
                            FirstGetVocabulary = false

                        } else {
                            // check update if data change
                            for (vocabularyNew in vocabularyList) {
                                val vocabularyDatabase = connection?.let {
                                    VocabularyDatabase(it, vocabularyNew)
                                }
                                val vocabularyOld = vocabularyDatabase?.select(vocabularyNew.id.toInt())

                                if (vocabularyOld != null) {
                                    checkUpdateData(vocabularyNew, vocabularyOld, connection)
                                } else {
                                    vocabularyDatabase?.insert()
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

    private fun checkUpdateData(vocabularyNew: Vocabulary, vocabularyOld: Vocabulary, connection: Connection) {

        if (vocabularyNew.toString().compareTo(vocabularyOld.toString()) == 0) return

        var dataChange = false

        val vocabularyNewStr = vocabularyNew.getString()
        val vocabularyOldStr = vocabularyOld.getString()
        val columnNewStr = vocabularyNewStr.split("/")
        val columnOldStr = vocabularyOldStr.split("/")
        var i = 0

        val myMutableMap: MutableMap<String, String> = mutableMapOf()
        val listDataChange: MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if (dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName: String = when (i) {
                    1 -> "romaji"
                    2 -> "hira"
                    3 -> "kata"
                    4 -> "groupe"
                    else -> "example"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, vocabularyNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            VocabularyDatabase(connection, vocabularyNew).update(myMutableMap, vocabularyNew.id.toInt())
            val tableChange = TableChange(vocabularyNew.id.toInt(), "vocabulary", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }
}
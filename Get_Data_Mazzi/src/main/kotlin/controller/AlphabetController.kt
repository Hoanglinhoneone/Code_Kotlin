package controller

import database.DatabaseHelper
import database.AlphabetDatabase
import model.Alphabet
import model.dataChange.ColumnChange
import model.dataChange.TableChange
import request.retrofit.RetrofitAlphabet
import java.sql.Connection
import java.sql.SQLException

class AlphabetController() {

    private var FirstGetAlphabet = true
    private lateinit var retrofitAlphabet : RetrofitAlphabet
    constructor(retrofitAlphabet: RetrofitAlphabet) : this () {
        this.retrofitAlphabet = retrofitAlphabet
    }

    fun getAlphabet() {
        try {
            val connection = DatabaseHelper.connect()
            retrofitAlphabet.getAlphabetList { alphabetList ->
                if (alphabetList != null) {

                    for (alphabet in alphabetList) {
                        println(alphabet.toString())
                    }

                    // first get => insert
                    if (FirstGetAlphabet){
                        for (alphabet in alphabetList) {
                            if (connection != null) {
                                AlphabetDatabase(connection, alphabet).insert()
                            }
                        }
                        FirstGetAlphabet = false

                    } else {
                        // check update if data change
                        for (alphabetNew in alphabetList) {
                            val alphabetDatabase = connection?.let {
                                AlphabetDatabase(it, alphabetNew)
                            }
                            val alphabetOld = alphabetDatabase?.select(alphabetNew.id.toInt())

                            if (alphabetOld != null) {
                                checkUpdateData(alphabetNew, alphabetOld, connection)
                            } else {
                                alphabetDatabase?.insert()
                            }
                        }
                    }
                } else {
                    // Xử lý trường hợp lỗi
                    println("list is null")
                }
                connection?.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun checkUpdateData(alphabetNew : Alphabet, alphabetOld : Alphabet, connection: Connection){

        if (alphabetNew.toString().compareTo(alphabetOld.toString()) == 0) return

        var dataChange = false

        val alphabetNewStr = alphabetNew.getString()
        val alphabetOldStr = alphabetOld.getString()
        val columnNewStr = alphabetNewStr.split("/")
        val columnOldStr = alphabetOldStr.split("/")
        var i = 0

        val myMutableMap : MutableMap<String, String> = mutableMapOf()
        val listDataChange : MutableList<ColumnChange> = mutableListOf()
        for (dataOld in columnOldStr) {
            val contentOfDataNew = columnNewStr[i++]
            if(dataOld.compareTo(contentOfDataNew) != 0) {
                dataChange = true
                val columnName : String = when(i) {
                    1 -> "romaji"
                    2 -> "hira"
                    3 -> "kata"
                    4 -> "groupe"
                    else -> "example"
                }
                myMutableMap[columnName] = contentOfDataNew
                val columnChange = ColumnChange(i, alphabetNew.id.toInt(), columnName, dataOld, contentOfDataNew)
                listDataChange.add(columnChange)
            }
        }

        if (dataChange) {
            AlphabetDatabase(connection, alphabetNew).update(myMutableMap, alphabetNew.id.toInt())
            val tableChange = TableChange(alphabetNew.id.toInt(), "alphabet", listDataChange)
            notification(tableChange)
        }
    }

    private fun notification(tableChange: TableChange) {

        // POST API

    }

}
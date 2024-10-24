package database.minna

import database.DatabaseHelper
import model.Alphabet
import model.minna.Grammar
import model.minna.Vocabulary
import java.sql.Connection
import java.sql.PreparedStatement

class VocabularyDatabase() {
    val tableName = "vocabulary"
    private lateinit var vocabulary: Vocabulary
    private lateinit var connection: Connection

    constructor( connection: Connection, vocabulary: Vocabulary ) : this() {
        this.vocabulary = vocabulary
        this.connection = connection
    }

    fun insert() {
        val sql =
            "INSERT INTO $tableName" +
                    " (id, lesson_id, hiragana, kanji, roumaji, mean, mean_unsigned, tag, favorite, kanji_id, cn_mean)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = vocabulary.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, vocabulary.lesson_id.toInt())
            preparedStatement.setString(3, vocabulary.hiragana)
            preparedStatement.setString(4, vocabulary.kanji)
            preparedStatement.setString(5, vocabulary.roumaji)
            preparedStatement.setString(6, vocabulary.mean)
            preparedStatement.setString(7, vocabulary.mean_unsigned)
            preparedStatement.setString(8, vocabulary.tag)
            preparedStatement.setString(9, vocabulary.favorite)
            preparedStatement.setString(10, vocabulary.kanji_id)
            preparedStatement.setString(11, vocabulary.cn_mean)

            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }
    fun update(myMutableMap: MutableMap<String, String>, id: Int ) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect : Int) : Vocabulary? {
        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val id = resultSet.getInt("id").toString()
            val lesson_id = resultSet.getString("lesson_id")
            val hiragana = resultSet.getString("hiragana")
            val kanji = resultSet.getString("kanji")
            val roumaji = resultSet.getString("roumaji")
            val mean = resultSet.getString("mean")
            val mean_unsigned = resultSet.getString("mean_unsigned")
            val tag = resultSet.getString("tag")
            val favorite = resultSet.getString("favorite")
            val kanji_id = resultSet.getString("kanji_id")
            val cn_mean = resultSet.getString("cn_mean")

            return Vocabulary(id, lesson_id, hiragana, kanji, mean,roumaji, mean_unsigned, tag, favorite, kanji_id, cn_mean)

        }
        return null
    }
}
package database.minna

import database.DatabaseHelper
import model.minna.Mondai
import java.sql.Connection
import java.sql.PreparedStatement

class MondaiDatabase() {
    val tableName = "mondai"
    private lateinit var mondai: Mondai
    private lateinit var connection: Connection

    constructor( connection: Connection, mondai: Mondai) : this() {
        this.mondai = mondai
        this.connection = connection
    }

    fun insert() {

        val sql =
            "INSERT INTO $tableName" +
                    " (id, lesson_id, namee, typee, question_num, answer)" +
                    " VALUES (?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = mondai.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, mondai.lesson_id.toInt())
            preparedStatement.setString(3, mondai.name)
            preparedStatement.setString(4, mondai.type)
            preparedStatement.setString(5, mondai.question_num)
            preparedStatement.setString(6, mondai.answer)


            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): Mondai? {
        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val id = resultSet.getInt("id").toString()
            val lesson_id = resultSet.getInt("lesson_id").toString()
            val name = resultSet.getString("name")
            val type = resultSet.getString("type")
            val question_num = resultSet.getString("question_num")
            val answer = resultSet.getString("answer")
            return Mondai(id, lesson_id, name, type, question_num, answer)
        }
        return null
    }
}
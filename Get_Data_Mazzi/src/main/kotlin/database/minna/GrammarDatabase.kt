package database.minna

import database.DatabaseHelper
import model.minna.Grammar
import java.sql.Connection
import java.sql.PreparedStatement

class GrammarDatabase() {
    val tableName = "grammar"
    private lateinit var grammar: Grammar
    private lateinit var connection: Connection

    constructor(connection: Connection, grammar: Grammar) : this() {
        this.grammar = grammar
        this.connection = connection
    }

    fun insert() {

        val sql =
            "INSERT INTO $tableName" +
                    " (id, leasson_id, namee, uname, content, tag, favorite)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = grammar.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, grammar.lesson_id.toInt())
            preparedStatement.setString(3, grammar.name)
            preparedStatement.setString(4, grammar.uname)
            preparedStatement.setString(5, grammar.content)
            preparedStatement.setString(6, grammar.tag)
            preparedStatement.setString(7, grammar.favorite)

            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): Grammar? {

        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val id = resultSet.getInt("id").toString()
            val lesson_id = resultSet.getString("lesson_id")
            val name = resultSet.getString("name")
            val uname = resultSet.getString("uname")
            val content = resultSet.getString("content")
            val tag = resultSet.getString("tag")
            val favorite = resultSet.getString("favorite")
            return Grammar(id, lesson_id, name, uname, content, tag, favorite)

        }
        return null
    }
}
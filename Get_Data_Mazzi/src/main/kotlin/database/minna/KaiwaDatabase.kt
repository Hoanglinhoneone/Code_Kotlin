package database.minna

import database.DatabaseHelper
import model.minna.Kaiwa
import java.sql.Connection
import java.sql.PreparedStatement

class KaiwaDatabase() {
    val tableName = "kaiwa"
    private lateinit var kaiwa: Kaiwa
    private lateinit var connection: Connection

    constructor( connection: Connection, kaiwa: Kaiwa) : this() {
        this.kaiwa = kaiwa
        this.connection = connection
    }

    fun insert() {

        val sql =
            "INSERT INTO $tableName" +
                    " (id, lesson_id, characterr, kaiwa, vi_mean, c_roumaji, j_roumaji)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = kaiwa.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, kaiwa.lesson_id.toInt())
            preparedStatement.setString(3, kaiwa.character)
            preparedStatement.setString(4, kaiwa.kaiwa)
            preparedStatement.setString(5, kaiwa.vi_mean)
            preparedStatement.setString(6, kaiwa.c_roumaji)
            preparedStatement.setString(7, kaiwa.j_roumaji)

            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): Kaiwa? {
        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val id = resultSet.getInt("id").toString()
            val lesson_id = resultSet.getInt("lesson_id").toString()
            val character = resultSet.getString("character")
            val kaiwa = resultSet.getString("kaiwa")
            val vi_mean = resultSet.getString("vi_mean")
            val c_roumaji = resultSet.getString("c_roumaji")
            val j_roumaji = resultSet.getString("j_roumaji")
            return Kaiwa(id, lesson_id, character, kaiwa, vi_mean, c_roumaji, j_roumaji)
        }
        return null
    }
}
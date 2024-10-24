package database.minna

import database.DatabaseHelper
import model.minna.Bunkei
import java.sql.Connection
import java.sql.PreparedStatement

class BunkeiDatabase {

    val tableName = "bunkei"
    private lateinit var bunkei: Bunkei
    private lateinit var connection: Connection

    constructor(connection: Connection, bunkei: Bunkei) {
        this.bunkei = bunkei
        this.connection = connection
    }

    fun insert() {

        val sql =
            "INSERT INTO $tableName" +
                    " (id, lesson_id, bunkei, vi_mean, roumaji, favorite)" +
                    " VALUES (?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = bunkei.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, bunkei.lesson_id.toInt())
            preparedStatement.setString(3, bunkei.bunkei)
            preparedStatement.setString(4, bunkei.vi_mean)
            preparedStatement.setString(5, bunkei.roumaji)
            preparedStatement.setString(6, bunkei.favorite)

            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): Bunkei? {

        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val id = resultSet.getInt("id").toString()
            val lesson_id = resultSet.getInt("lesson_id").toString()
            val bunkei = resultSet.getString("bunkei")
            val vi_mean = resultSet.getString("vi_mean")
            val roumaji = resultSet.getString("roumaji")
            val favorite = resultSet.getString("favorite")
            return Bunkei(id, lesson_id, bunkei, vi_mean, roumaji, favorite)

        }

        return null

    }
}
package database.minna

import database.DatabaseHelper
import model.minna.Reibun
import java.sql.Connection
import java.sql.PreparedStatement

class ReibunDatabase() {
    val tableName = "reibun"
    private lateinit var reibun: Reibun
    private lateinit var connection: Connection

    constructor( connection: Connection, reibun: Reibun) : this() {
        this.reibun = reibun
        this.connection = connection
    }

    fun insert() {

        val sql =
            "INSERT INTO $tableName" +
                    " (id, lesson_id, typee, reibun, vi_mean, j_roumaji)" +
                    " VALUES (?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = reibun.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, reibun.lesson_id.toInt())
            preparedStatement.setString(3, reibun.type)
            preparedStatement.setString(4, reibun.reibun)
            preparedStatement.setString(5, reibun.vi_mean)
            preparedStatement.setString(6, reibun.roumaji)


            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): Reibun? {
        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val id = resultSet.getInt("id").toString()
            val lesson_id = resultSet.getInt("lesson_id").toString()
            val type = resultSet.getString("type")
            val reibun = resultSet.getString("reibun")
            val vi_mean = resultSet.getString("vi_mean")
            val roumaji = resultSet.getString("roumaji")

            return Reibun(id, lesson_id, type, reibun, vi_mean, roumaji)

        }
        return null
    }
}
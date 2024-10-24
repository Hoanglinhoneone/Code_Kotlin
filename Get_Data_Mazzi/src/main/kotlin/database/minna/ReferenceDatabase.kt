package database.minna

import database.DatabaseHelper
import model.minna.Reference
import java.sql.Connection
import java.sql.PreparedStatement

class ReferenceDatabase() {
    val tableName = "reference"
    private lateinit var reference: Reference
    private lateinit var connection: Connection

    constructor(connection: Connection, reference: Reference) : this() {
        this.reference = reference
        this.connection = connection
    }

    fun insert() {

        val sql =
            "INSERT INTO $tableName" +
                    " (id, lesson_id, japanese, roumaji, vietnamese, note)" +
                    " VALUES (?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = reference.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, reference.lesson_id.toInt())
            preparedStatement.setString(3, reference.japanese)
            preparedStatement.setString(4, reference.roumaji)
            preparedStatement.setString(5, reference.vietnamese)
            preparedStatement.setString(6, reference.note)

            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): Reference? {
        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {

            val id = resultSet.getInt("id").toString()
            val lesson_id = resultSet.getInt("lesson_id").toString()
            val japanese = resultSet.getString("japanese")
            val roumaji = resultSet.getString("roumaji")
            val vietnamese = resultSet.getString("vietnamese")
            val note = resultSet.getString("note")
            return Reference(id, lesson_id, japanese, roumaji, vietnamese, note)

        }
        return null
    }
}
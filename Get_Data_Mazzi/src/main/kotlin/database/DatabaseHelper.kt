package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

object DatabaseHelper {
    private const val URL = "jdbc:mysql://localhost:3306/mymazzi"
    private const val USER = "root"
    private const val PASSWORD = "hnl305@aZ"

    fun connect(): Connection? {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    fun close(connection: Connection) {
        try {
            connection.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun checkRecordExists(connection: Connection, id: Int, tableName: String): Boolean {
        val sqlCheck = "SELECT COUNT(*) FROM $tableName WHERE id =?"
        val checkStatement: PreparedStatement? = connection.prepareStatement(sqlCheck)
        return try {
            checkStatement?.setInt(1, id)
            if (checkStatement != null) {
                val resultCheck = checkStatement.executeQuery()
                resultCheck.next() && resultCheck.getInt(1) == 0
            } else {
                false
            }
        } catch (e: SQLException) {
            println("SQL error: ${e.message}")
            false
        } finally {
            checkStatement?.close()
        }
    }

    fun updateRecord(tableName: String, connection: Connection, myMutableMap: MutableMap<String, String>, id: Int) {
        val setClause = myMutableMap.entries.joinToString(", ") { " $it = ? " }
        val sql = "UPDATE $tableName SET $setClause WHERE id = ? "
        val preparedStatement = connection.prepareStatement(sql)

        var index = 1
        for ((_, value) in myMutableMap) {
            preparedStatement.setInt(index, id)
            index++
        }

        preparedStatement.setInt(index, id)
        val rowUpdated = preparedStatement.executeUpdate()
        if (rowUpdated > 0) {
            println("update successful")
        } else {
            println("update error!")
        }
    }
}
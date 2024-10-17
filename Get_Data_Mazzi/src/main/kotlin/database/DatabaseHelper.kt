package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseHelper {
    private const val URL = "jdbc:mysql://localhost:3306/mymazzi"
    private const val USER = "root"
    private const val  PASSWORD = "hnl305@aZ"

    fun connect() : Connection? {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e : SQLException)  {
            e.printStackTrace()
            null
        }
    }

    fun closeConnection(connection: Connection) {
        try {
            connection.close()
        } catch (e : SQLException) {
            e.printStackTrace()
        }
    }
}
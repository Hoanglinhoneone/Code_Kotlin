package database.minna

import database.DatabaseHelper
import model.minna.commom.AudioFile
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class AudioFileDatabase() {

    val tableName = "audiofile"
    private lateinit var audioFile: AudioFile
    private lateinit var connection: Connection

    constructor(connection: Connection, audioFile: AudioFile) : this() {
        this.audioFile = audioFile
        this.connection = connection
    }


    fun insert() {
        try {
            val sql =
                "INSERT INTO $tableName" +
                        " (id, lesson_id, indx, typee, link , status)" +
                        " VALUES (?, ?, ?, ?, ?, ?)"

            val id = audioFile.id.toInt()
            val exist = DatabaseHelper.checkRecordExists(connection, id, tableName)

            val preparedStatement: PreparedStatement? = connection.prepareStatement(sql)
            if (preparedStatement == null) {
                println("Connection is null. Cannot prepare preparedStatement.")
                return
            }

            if (exist) {

                preparedStatement.setInt(1, id)
                preparedStatement.setInt(2, audioFile.lesson.toInt())
                preparedStatement.setInt(3, audioFile.indx.toInt())
                preparedStatement.setString(4, audioFile.type)
                preparedStatement.setString(5, audioFile.link)
                preparedStatement.setString(6, audioFile.status)

                preparedStatement.addBatch()
                preparedStatement.execute()
            }

            preparedStatement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): AudioFile? {
        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            val id = resultSet.getInt("id").toString()
            val lesson = resultSet.getInt("lesson").toString()
            val indx = resultSet.getInt("indx").toString()
            val type = resultSet.getString("type")
            val link = resultSet.getString("link")
            val status = resultSet.getString("status")
            return AudioFile(id, lesson, indx, type, link, status)

        }
        return null
    }
}
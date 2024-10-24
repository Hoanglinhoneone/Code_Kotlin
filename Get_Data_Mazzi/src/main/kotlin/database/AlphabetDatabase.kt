package database

import model.Alphabet
import java.sql.Connection
import java.sql.PreparedStatement

class AlphabetDatabase() {

    val tableName = "alphabet"
    private lateinit var alphabet: Alphabet
    private lateinit var connection: Connection

    constructor(connection: Connection, alphabet: Alphabet) : this() {
        this.alphabet = alphabet;
        this.connection = connection
    }

    fun insert() {
        val sql =
            "INSERT INTO $tableName" +
                    " (id, romaji, hira, kata, groupe, example)" +
                    " VALUES (?, ?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = this.connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = this.alphabet.id.toInt()
        val exist = DatabaseHelper.checkRecordExists(this.connection, id, tableName)

        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setString(2, alphabet.romaji)
            preparedStatement.setString(3, alphabet.hira)
            preparedStatement.setString(4, alphabet.kata)
            preparedStatement.setString(5, alphabet.groupe)
            preparedStatement.setString(6, alphabet.example)
            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

    fun update(myMutableMap: MutableMap<String, String>, id: Int) {
        DatabaseHelper.updateRecord(tableName, connection, myMutableMap, id)
    }

    fun select(idSelect: Int): Alphabet? {
        val sql = "SELECT * FROM $tableName WHERE id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {

            val id = resultSet.getInt("id").toString()
            val romaji = resultSet.getString("romaji")
            val hira = resultSet.getString("hira")
            val kata = resultSet.getString("kata")
            val groupe = resultSet.getString("groupe")
            val example = resultSet.getString("example")
            return Alphabet(id, romaji, hira, kata, groupe, example)

        }
        return null
    }
}
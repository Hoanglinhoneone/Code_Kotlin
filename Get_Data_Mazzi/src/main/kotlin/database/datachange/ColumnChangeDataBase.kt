package database.datachange
/*
import database.DatabaseHelper
import model.dataChange.ColumnChange
import java.sql.Connection
import java.sql.PreparedStatement

class ColumnChangeDataBase() {

    val tableName = "columnChangeDataBase"
    private lateinit var connection: Connection

    constructor(connection: Connection) : this() {
        this.connection = connection
    }

    fun insertColumnChange(columnChange: ColumnChange) {
        val sql =
            "INSERT INTO $tableName" +
                    " (id, changeNotificationId, columName, contentOld, contentNew)" +
                    " VALUES (?, ?, ?, ?, ?)"

        val preparedStatement: PreparedStatement? = this.connection.prepareStatement(sql)
        if (preparedStatement == null) {
            println("Connection is null. Cannot prepare preparedStatement.")
            return
        }
        val id = columnChange.id
        val exist = DatabaseHelper.checkrecordExists(this.connection, id, tableName)
        if (exist) {
            preparedStatement.setInt(1, id)
            preparedStatement.setInt(2, columnChange.changeNotificationId)
            preparedStatement.setString(3, columnChange.columName)
            preparedStatement.setString(4, columnChange.contentOld)
            preparedStatement.setString(5, columnChange.contentNew)
            preparedStatement.addBatch()
            preparedStatement.execute()
        }
        preparedStatement.close()
    }

//    fun updateColumnChange(myMutableMap: MutableMap<String, String>, id: Int ) {
//        val setClause = myMutableMap.entries.joinToString(", ") { " $it = ? " }
//        val sql = "UPDATE $tableName SET $setClause WHERE id = ? "
//        val preparedStatement = connection.prepareStatement(sql)
//
//        // gán giá trị cho các cột trong SET từ Mutablemap
//        var  index = 1
//        for ((_, value) in myMutableMap) {
//            preparedStatement.setInt(index, id)
//            index++
//        }
//
//        preparedStatement.setInt(index, id)
//        val rowUpdated = preparedStatement.executeUpdate()
//        if(rowUpdated > 0) {
//            println("update successful")
//        } else {
//            println("khong co ban ghi nao duoc update")
//        }
//    }


    fun selectColumnChange(idSelect : Int) : List<ColumnChange> {
        val columnChangeList = mutableListOf<ColumnChange>()
        val sql = "SELECT * FROM $tableName WHERE changeNotificationId = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, idSelect)
        val resultSet = preparedStatement.executeQuery()

        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val changeNotificationId = resultSet.getInt("changeNotificationId")
            val columName = resultSet.getString("columName")
            val contentOld = resultSet.getString("contentOld")
            val contentNew = resultSet.getString("contentNew")
            columnChangeList.add(ColumnChange(id, changeNotificationId, columName, contentOld,contentNew))
        }
        return columnChangeList
    }
}
*/
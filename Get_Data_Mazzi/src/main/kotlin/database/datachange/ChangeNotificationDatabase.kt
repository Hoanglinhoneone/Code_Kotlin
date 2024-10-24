package database.datachange

import java.sql.Connection
/*
class ChangeNotificationDatabase() {

    val tableName = "changeNotification"
    private lateinit var connection: Connection

    constructor(connection: Connection) : this() {
        this.connection = connection
    }

//    fun insertChangeNotificationDatabase(changeNotification: ChangeNotification) {
//        val sql =
//            "INSERT INTO $tableName (id, tablename) VALUES (?, ?)"
//
//        val preparedStatement: PreparedStatement? = this.connection.prepareStatement(sql)
//        if (preparedStatement == null) {
//            println("Connection is null. Cannot prepare preparedStatement.")
//            return
//        }
//        val id = changeNotification.id
//        val exist = DatabaseHelper.checkrecordExists(this.connection, id, tableName)
//        if (exist) {
//            preparedStatement.setInt(1, id)
//            preparedStatement.setString(2, changeNotification.tableName)
//
//            preparedStatement.addBatch()
//            preparedStatement.execute()
//        }
//        preparedStatement.close()
//    }

//    fun updateChangeNotificationDatabase(myMutableMap: MutableMap<String, String>, id: Int ) {
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


//    fun selectChangeNotificationDatabase() : List<ChangeNotification> {
//        val changeNotificationList  = mutableListOf<ChangeNotification>()
//        val sql = "SELECT * FROM $tableName"
//        val preparedStatement = connection.prepareStatement(sql)
//        val resultSet = preparedStatement.executeQuery()
//        while (resultSet.next()) {
//            val id = resultSet.getInt("id")
//            val tableName = resultSet.getString("tablename")
//            val columnChangeList = ColumnChangeDataBase(connection).selectColumnChange(id)
//            changeNotificationList.add(ChangeNotification(id, tableName, columnChangeList))
//        }
//        return changeNotificationList
//    }

}
*/
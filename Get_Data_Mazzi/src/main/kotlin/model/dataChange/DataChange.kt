package model.dataChange

import kotlinx.serialization.Serializable

@Serializable
data class ColumnChange(
    val id : Int,
    val tableChangeId : Int,
    val columName: String,
    val contentOld: String,
    val contentNew: String

)

@Serializable
data class TableChange(
    val recordId : Int,
    val tableName: String,
    val changes: List<ColumnChange>
)
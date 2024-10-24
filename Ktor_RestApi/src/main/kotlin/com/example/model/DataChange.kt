package com.example.model

import kotlinx.serialization.Serializable

@Serializable
object DataChange {
    private val datas = mutableListOf(
        TableChange(1, "romaji",
            changes = listOf(
                ColumnChange(1, 1, "type", "aaa", "bbb"),
                ColumnChange(2, 1, "type", "aaa", "bbb"),
                ColumnChange(3, 1, "type", "aaa", "bbb"),
        )),
        TableChange(1, "romaji",
            changes = listOf(
                ColumnChange(1, 1, "type", "aaa", "bbb"),
                ColumnChange(2, 1, "type", "aaa", "bbb"),
                ColumnChange(3, 1, "type", "aaa", "bbb"),
        )),
        TableChange(2, "romaji",
            changes = listOf(
                ColumnChange(1, 1, "type", "aaa", "bbb"),
                ColumnChange(2, 1, "type", "aaa", "bbb"),
                ColumnChange(3, 1, "type", "aaa", "bbb"),
        )),
        TableChange(3, "romaji",
            changes = listOf(
                ColumnChange(1, 1, "type", "aaa", "bbb"),
                ColumnChange(2, 1, "type", "aaa", "bbb"),
                ColumnChange(3, 1, "type", "aaa", "bbb"),
        )),
        TableChange(4, "romaji",
            changes = listOf(
                ColumnChange(1, 1, "type", "aaa", "bbb"),
                ColumnChange(2, 1, "type", "aaa", "bbb"),
                ColumnChange(3, 1, "type", "aaa", "bbb"),
        )),
    )

    fun allTableChange() : List<TableChange> = datas
    private fun getbyId(id: Int) = datas.find {
        it.id == id
    }
    fun addTableChange(tableChange: TableChange) {
        if (getbyId(tableChange.id) != null) {
            throw IllegalStateException("Cannot duplicate tableChange id !")
        }
        datas.add(tableChange)
    }
}
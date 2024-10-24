package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class TableChange(
    val id : Int,
    val tableName: String,
    val changes: List<ColumnChange>
)
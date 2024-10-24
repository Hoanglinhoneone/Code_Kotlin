package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class ColumnChange(
    val id : Int,
    val tableChangeId : Int,
    val columName: String,
    val contentOld: String,
    val contentNew: String
)



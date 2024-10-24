package model.minna

data class Grammar(
    val id : String,
    val lesson_id : String,
    val name : String,
    val uname : String,
    val content : String,
    val tag : String,
    val favorite : String,
) {
    fun getString() : String {
        return "$id/$lesson_id/$name/$uname/$content/$tag/$favorite"
    }
}
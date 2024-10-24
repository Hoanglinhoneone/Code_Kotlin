package model.minna

data class Mondai(
    val id	        :	String,
    val lesson_id   :	String,
    val name        :	String,
    val type        :	String,
    val question_num:	String,
    val answer      :	String,
) {
    fun getString() : String {
        return "$id/$lesson_id/$name/$type/$question_num/$answer"
    }
}

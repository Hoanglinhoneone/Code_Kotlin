package model.minna

data class Bunkei(
    val id	        :	String,
    val lesson_id	:	String,
    val bunkei	    :	String,
    val vi_mean	    :	String,
    val roumaji	    :	String,
    val favorite	:   String
) {
    fun getString() : String {
        return "$id/$lesson_id/$bunkei/$vi_mean/$roumaji/$favorite"
    }
}

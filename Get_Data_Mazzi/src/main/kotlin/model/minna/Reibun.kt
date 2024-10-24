package model.minna

data class Reibun(
    val id	        :	String,
    val lesson_id	:	String,
    val type	    :	String,
    val reibun	    :	String,
    val vi_mean 	:	String,
    val roumaji	    :	String,
) {
    fun getString() : String {
        return "$id/$lesson_id/$type/$reibun/$vi_mean/$roumaji"
    }
}

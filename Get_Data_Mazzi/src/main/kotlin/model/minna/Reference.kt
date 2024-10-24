package model.minna

data class Reference(
    val id	        :	String,
    val lesson_id	:	String,
    val japanese	:	String,
    val roumaji	    :	String,
    val vietnamese	:	String,
    val note	    : String,
)
{
    fun getString() : String {
        return "$id/$lesson_id/$japanese/$roumaji/$vietnamese/$note"
    }

}

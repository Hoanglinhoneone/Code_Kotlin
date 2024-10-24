package model.minna

data class Kaiwa(

    var id : String,
    var lesson_id: String,
    var character: String,
    var kaiwa: String,
    var vi_mean: String,
    var c_roumaji: String,
    var j_roumaji: String
) {
    fun getString() : String {
        return "$id/$lesson_id/$character/$kaiwa/$vi_mean/$c_roumaji/$j_roumaji"
    }
}
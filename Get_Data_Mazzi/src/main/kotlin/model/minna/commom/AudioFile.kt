package model.minna.commom

data class AudioFile(
    var id : String,
    var lesson : String,
    var indx : String,
    var type: String,
    var link:String,
    var status : String
) {
    fun getString() : String {
        return "$id/$lesson/$indx/$type/$link/$status"
    }
}
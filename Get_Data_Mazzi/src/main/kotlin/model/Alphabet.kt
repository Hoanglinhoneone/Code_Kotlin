package model

data class Alphabet(
    val id : String,
    val romaji: String,
    val hira: String,
    val kata: String,
    val groupe: String,
    val example: String,
) {
    fun getString() : String {
        return "$id/$romaji/$hira/$kata/$groupe/$example"
    }
}

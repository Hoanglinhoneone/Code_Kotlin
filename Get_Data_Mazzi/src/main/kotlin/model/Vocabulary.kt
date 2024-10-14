package model

data class Vocabulary(
    private var id : String,
    private var lesson_id : String,
    private var hiragana : String,
    private var kanji: String,
    private var roumaji: String,
    private var mean: String,
    private var mean_unsigned: String,
    private var tag: String,
    private var favorite: String,
    private var kanji_id: String,
    private val cn_mean : String,
    )
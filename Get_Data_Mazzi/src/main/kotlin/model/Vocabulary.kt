package model

data class Vocabulary(
     var id : String,
     var lesson_id : String,
     var hiragana : String,
     var kanji: String,
     var roumaji: String,
     var mean: String,
     var mean_unsigned: String,
     var tag: String,
     var favorite: String,
     var kanji_id: String,
     val cn_mean : String,
    )
package api

import model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //link api:

    //https://mina.mazii.net/api/getKotoba.php?lessonid=1
    @GET("/api/getKotoba.php")
    fun getListenLesson(@Query("lessonid") lessonid : String) : Call<List<Vocabulary>>

    //https://mina.mazii.net/api/getListenLession.php?lesson=1&type=Kotoba
    @GET("/api/getListenLession.php")
    fun getAudioFile(@Query("lesson") lesson: String, @Query("type") type: String): Call<List<AudioFile>>

    //https://mina.mazii.net/api/getGrammar.php?lessonid=3
    @GET("/api/getGrammar.php")
    fun getGrammar(@Query("lessonid") lessonid: String) : Call<List<Grammar>>

    //https://mina.mazii.net/api/getKaiwa.php?lessonid=1
    @GET("/api/getKaiwa.php")
    fun getKaiWa(@Query("lessonid") lessonid: String) : Call<List<Kaiwa>>

    //https://mina.mazii.net/api/getMondai.php?lessonid=1
    @GET("/api/getMondai.php")
    fun getMondai(@Query("lessonid") lessonid: String) : Call<List<Mondai>>

//    https://mina.mazii.net/api/getBunkei.php?lessonid=1
    @GET("/api/getBunkei.php")
    fun getBunkei(@Query("lessonid") lessonid: String) : Call<List<Bunkei>>

//    https://mina.mazii.net/api/getReibun.php?lessonid=1
    @GET("/api/getReibun.php")
    fun getReibun(@Query("lessonid") lessonid: String) : Call<List<Reibun>>

    //    https://mina.mazii.net/api/getReference.php?lessonid=1
    @GET("/api/getReference.php")
    fun getReference(@Query("lessonid") lessonid: String) : Call<List<Reference>>

    // https://mina.mazii.net/api/getKana.php
    @GET("/api/getKana.php")
    fun getAlphabet() : Call<List<Alphabet>>
}
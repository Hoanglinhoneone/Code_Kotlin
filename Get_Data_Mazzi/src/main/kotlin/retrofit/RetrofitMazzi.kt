package retrofit

import api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitMazzi {
    //    https://mina.mazii.net/api/getKotoba.php?lessonid=1
    private val domainUrl = "https://mina.mazii.net"
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(domainUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}
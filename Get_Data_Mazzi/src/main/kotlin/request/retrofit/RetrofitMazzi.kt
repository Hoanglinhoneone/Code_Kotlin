package request.retrofit

import request.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun <T> getListOneParam(callFunction: (String) -> Call<List<T>>, index: Int, callback: (List<T>?) -> Unit) {
        val call = callFunction("$index")
        call.enqueue(object  : Callback<List<T>> {
            override fun onResponse(p0: Call<List<T>>, p1: Response<List<T>>) {
                if(p1.isSuccessful) {
                    val list = p1.body()
                    if (list != null) {
                        callback(list)
                    }
                    else {
                        println("Received empty body...")
                        callback(null)
                    }
                }
                else {
                    println("Error: ${p1.message()}  Code : ${p1.code()}")
                }
            }

            override fun onFailure(p0: Call<List<T>>, p1: Throwable) {
                p1.printStackTrace()
                callback(null)
            }
        })
    }
    fun <T> getListTwoParam(callFunction: (String, String) -> Call<List<T>>, index : Int, type : String, callback: (List<T>?) -> Unit) {
        val call = callFunction("$index", type)
        call.enqueue(object : Callback<List<T>> {
            override fun onResponse(p0: Call<List<T>>, p1: Response<List<T>>) {

                if (p1.isSuccessful) {
                    val list = p1.body()
                    if(list != null) {
                        callback(list)
                    }
                    else {
                        println("receiver empty body...")
                        callback(null)
                    }
                }

                else {
                    println("Error: ${p1.message()} Code: ${p1.code()}")
                }

            }
            override fun onFailure(p0: Call<List<T>>, p1: Throwable) {
                p1.printStackTrace()
                callback(null)
            }
        })
    }
}
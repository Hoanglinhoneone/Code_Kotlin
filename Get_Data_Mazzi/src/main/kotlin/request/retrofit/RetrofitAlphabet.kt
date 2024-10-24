package request.retrofit

import model.Alphabet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitAlphabet() {
    private val retrofitMazzi =  RetrofitMazzi()

    fun getAlphabetList(callback: (List<Alphabet>?) -> Unit) {
        val apiService = retrofitMazzi.getApiService()
        val call = apiService.getAlphabet()

        call.enqueue(object : Callback<List<Alphabet>> {

            override fun onResponse(call: Call<List<Alphabet>>, response: Response<List<Alphabet>>) {
                if (response.isSuccessful) {
                    val alphabetList = response.body()
                    if (alphabetList != null) {
                        callback(alphabetList)
                    } else {
                        println("Error: Received empty body")
                        callback(null)
                    }
                } else {
                    println("Error: ${response.message()} (Code: ${response.code()})")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Alphabet>>, t: Throwable) {
                t.printStackTrace()
                callback(null)
            }
        })
    }
}

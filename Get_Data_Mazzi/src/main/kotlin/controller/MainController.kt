package controller

import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import model.*
import retrofit.RetrofitMazzi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

class MainController {
//    val logger: Logger = LoggerFactory.getLogger("")
    private val url = "jdbc:mysql://localhost:3306/mymazzi"
    private val user = "root"
    private val password = "hnl305@aZ"

//    val connection: Connection = DriverManager.getConnection(url, user, password)


    fun getFullAudioFile() {

        getAudioFile("Kotoba")
        getAudioFile("Kaiwa")
        getAudioFile("Mondai")
        getAudioFile("Bunkei")
        getAudioFile("Reibun")
    }

        fun getVocabulary() {
        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()

            val call = apiService.getListenLesson("$i")
            val vocabularyCallback = object : Callback<List<Vocabulary>> {
                override fun onResponse(p0: Call<List<Vocabulary>>, p1: Response<List<Vocabulary>>) {
                    if (!p1.isSuccessful) {
                        println("loi")
                        return
                    } else if (p1.body() == null) {
                        println("null")
                        return
                    }
                    val vocabularyList = p1.body()
                    if (vocabularyList != null) {
                        for (j in vocabularyList) {
                            println(j.toString())
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Vocabulary>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            }
            call.enqueue(vocabularyCallback)
        }
    }

    fun getGrammar() {
        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getGrammar("$i")
            call.enqueue(object : Callback<List<Grammar>> {
                override fun onResponse(p0: Call<List<Grammar>>, p1: Response<List<Grammar>>) {
                    if (!p1.isSuccessful) return
                    else if (p1.body() == null) return
                    val listGrammar = p1.body()
                    if (listGrammar != null) {
                        for (j in listGrammar) {
                            println(j.toString())
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Grammar>>, p1: Throwable) {
                    println("Loi roi")
                }
            })
        }
    }

    fun getKaiWa() {
        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getKaiWa("$i")
            call.enqueue(object : Callback<List<Kaiwa>> {
                override fun onResponse(p0: Call<List<Kaiwa>>, p1: Response<List<Kaiwa>>) {
                    if (!p1.isSuccessful) return
                    else if (p1.body() == null) return
                    val listKaiWa = p1.body()
                    if (listKaiWa != null) {
                        for (j in listKaiWa) {
                            println(j.toString())
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Kaiwa>>, p1: Throwable) {
                    p1.printStackTrace()
                }

            })
        }
    }

    fun getReference() {
        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getReference("$i")
            call.enqueue(object : Callback<List<Reference>> {
                override fun onResponse(p0: Call<List<Reference>>, p1: Response<List<Reference>>) {
                    if (!p1.isSuccessful) return
                    else if (p1.body() == null) return
                    val listReference = p1.body()
                    if (listReference != null) {
                        for (j in listReference) {
                            println(j.toString())
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Reference>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            })
        }
    }

    fun getMondai() {
        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getMondai("$i")

            call.enqueue(object : Callback<List<Mondai>> {
                override fun onResponse(p0: Call<List<Mondai>>, p1: Response<List<Mondai>>) {
                    if (p1.body() == null && !p1.isSuccessful) {
                        return
                    }
                    val listMondai = p1.body()
                    if (listMondai != null) {
                        for (j in listMondai) {
                            println(j.toString())
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Mondai>>, p1: Throwable) {
                    p1.printStackTrace()
                }

            })
        }
    }

    fun getBunkei() {

        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getBunkei("$i")

            call.enqueue(object : Callback<List<Bunkei>> {
                override fun onResponse(p0: Call<List<Bunkei>>, p1: Response<List<Bunkei>>) {
                    if (p1.body() == null && !p1.isSuccessful) {
                        return
                    }
                    val listBunkei = p1.body()
                    if (listBunkei != null) {
                        for (j in listBunkei) {
                            println(j.toString())
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Bunkei>>, p1: Throwable) {
                    p1.printStackTrace()
                }

            })
        }

    }

    fun getReibun() {

        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getReibun("$i")

            call.enqueue(object : Callback<List<Reibun>> {
                override fun onResponse(p0: Call<List<Reibun>>, p1: Response<List<Reibun>>) {
                    if (p1.body() == null && !p1.isSuccessful) {
                        return
                    }
                    val listReibun = p1.body()
                    if (listReibun != null) {
                        for (j in listReibun) {
                            println(j.toString())
                        }
                    }
                }

                override fun onFailure(p0: Call<List<Reibun>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            })
        }

    }


    private fun getAudioFile(type: String) {
        val connection: Connection = DriverManager.getConnection(url, user, password)

        for (i in 1..50) {
            val retrofitMazzi = RetrofitMazzi()
            val apiService = retrofitMazzi.getApiService()

            val call = apiService.getAudioFile(i.toString(), type) // Gọi phương thức
            call.enqueue(object : Callback<List<AudioFile>> {
                override fun onResponse(p0: Call<List<AudioFile>>, p1: Response<List<AudioFile>>) {
                    if (!p1.isSuccessful) {
                        println("loi")
                        return
                    } else if (p1.body() == null) {
                        println("null")
                        return
                    }
                    val listAudioFile = p1.body() as MutableList<AudioFile>
                    for (audiofile in listAudioFile) {
                        println(audiofile)
                    }
                    try {
                        val sql =
                            "INSERT INTO audiofile (id, lesson_id, indx, typee, link, status) VALUES (?, ?, ?, ?, ?, ?)"
                        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

                        for (audiofile in listAudioFile) {
                            println(audiofile)
                            val id = audiofile.id.toInt()

                            // Kiểm tra xem id đã tồn tại chưa
                            val checkSql = "SELECT COUNT(*) FROM audiofile WHERE id = ?"
                            val checkStatement: PreparedStatement = connection.prepareStatement(checkSql)
                            checkStatement.setInt(1, id)
                            val resultSet = checkStatement.executeQuery()

                            if (resultSet.next() && resultSet.getInt(1) == 0) {
                                // Nếu id chưa tồn tại, thêm vào batch
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, audiofile.lesson.toInt())
                                preparedStatement.setInt(3, audiofile.indx.toInt())
                                preparedStatement.setString(4, audiofile.type)
                                preparedStatement.setString(5, audiofile.link)
                                preparedStatement.setString(6, audiofile.status)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }

                        // Thực hiện batch insert
                        preparedStatement.executeBatch()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(p0: Call<List<AudioFile>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            })
        }
        connection.close()
    }

    fun closeConnect() {
//        connection.close()
    }
}
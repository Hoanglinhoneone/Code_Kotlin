package controller


import database.DatabaseHelper
import kotlinx.coroutines.delay
import model.*
import retrofit.RetrofitMazzi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.sql.PreparedStatement
import java.util.Properties
import java.util.concurrent.TimeUnit

class MainController {
    private val retrofitMazzi = RetrofitMazzi()
    suspend fun getFullAudioFile() {
        getAudioFile("Kotoba")
        delay(5000)
        getAudioFile("Kaiwa")
        delay(5000)
        getAudioFile("Mondai")
        delay(5000)
        getAudioFile("Bunkei")
        delay(5000)
        getAudioFile("Reibun")
    }

    fun getVocabulary() {
        for (i in 1..50) {
            val apiService = retrofitMazzi.getApiService()

            val call = apiService.getListenLesson("$i")
            call.enqueue(object : Callback<List<Vocabulary>> {
                override fun onResponse(p0: Call<List<Vocabulary>>, p1: Response<List<Vocabulary>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val vocabularyList = p1.body() as MutableList
                        for (j in vocabularyList) {
                            println(j.toString())
                        }
                        val sql =
                            "INSERT INTO vocabulary" +
                                    " (id, lesson_id, hiragana, kanji, roumaji, mean, mean_unsigned, tag, favorite, kanji_id, cn_mean)" +
                                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

                        val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                        if (preparedStatement == null) {
                            println("Connection is null. Cannot prepare preparedStatement.")
                            return
                        }
                        for (vocabulary in vocabularyList) {
                            val id = vocabulary.id.toInt()
                            val sqlCheck = "SELECT COUNT(*) FROM vocabulary WHERE id = ?"

                            val checkStatement: PreparedStatement? = connection?.prepareStatement(sqlCheck)
                            if (checkStatement == null) {
                                println("Can not preprare checkStatement")
                                return
                            }
                            checkStatement.setInt(1, id)
                            val resultCheck = checkStatement.executeQuery()
                            if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, vocabulary.lesson_id.toInt())
                                preparedStatement.setString(3, vocabulary.hiragana)
                                preparedStatement.setString(4, vocabulary.kanji)
                                preparedStatement.setString(5, vocabulary.roumaji)
                                preparedStatement.setString(6, vocabulary.mean)
                                preparedStatement.setString(7, vocabulary.mean_unsigned)
                                preparedStatement.setString(8, vocabulary.tag)
                                preparedStatement.setString(9, vocabulary.favorite)
                                preparedStatement.setString(10, vocabulary.kanji_id)
                                preparedStatement.setString(11, vocabulary.cn_mean)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }
                        preparedStatement.execute()
                        connection.close()
                    } else {
                        println("loi")
                        return
                    }
                }

                override fun onFailure(p0: Call<List<Vocabulary>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            })
        }
    }

    fun getGrammar() {
        for (i in 1..50) {
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getGrammar("$i")
            call.enqueue(object : Callback<List<Grammar>> {
                override fun onResponse(p0: Call<List<Grammar>>, p1: Response<List<Grammar>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val grammarList = p1.body() as MutableList
                        for (j in grammarList) {
                            println(j.toString())
                        }
                        val sql =
                            "INSERT INTO grammar" +
                                    " (id, leasson_id, namee, uname, content, tag, favorite)" +
                                    " VALUES (?, ?, ?, ?, ?, ?, ?)"

                        val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                        if (preparedStatement == null) {
                            println("Connection is null. Cannot prepare preparedStatement.")
                            return
                        }
                        for (grammar in grammarList) {
                            val id = grammar.id.toInt()
                            val sqlCheck = "SELECT COUNT(*) FROM grammar WHERE id = ?"

                            val checkStatement: PreparedStatement? = connection?.prepareStatement(sqlCheck)
                            if (checkStatement == null) {
                                println("Can not preprare checkStatement")
                                return
                            }
                            checkStatement.setInt(1, id)
                            val resultCheck = checkStatement.executeQuery()
                            if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, grammar.lesson_id.toInt())
                                preparedStatement.setString(3, grammar.name)
                                preparedStatement.setString(4, grammar.uname)
                                preparedStatement.setString(5, grammar.content)
                                preparedStatement.setString(6, grammar.tag)
                                preparedStatement.setString(7, grammar.favorite)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }
                        preparedStatement.execute()
                        connection.close()
                    } else {
                        println("loi")
                        return
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
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getKaiWa("$i")
            call.enqueue(object : Callback<List<Kaiwa>> {
                override fun onResponse(p0: Call<List<Kaiwa>>, p1: Response<List<Kaiwa>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val kaiwaList = p1.body() as MutableList
                        for (j in kaiwaList) {
                            println(j.toString())
                        }
                        val sql =
                            "INSERT INTO kaiwa" +
                                    " (id, lesson_id, characterr, kaiwa, vi_mean, c_roumaji, j_roumaji)" +
                                    " VALUES (?, ?, ?, ?, ?, ?, ?)"

                        val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                        if (preparedStatement == null) {
                            println("Connection is null. Cannot prepare preparedStatement.")
                            return
                        }
                        for (kaiwa in kaiwaList) {
                            val id = kaiwa.id.toInt()
                            val sqlCheck = "SELECT COUNT(*) FROM kaiwa WHERE id = ?"

                            val checkStatement: PreparedStatement? = connection?.prepareStatement(sqlCheck)
                            if (checkStatement == null) {
                                println("Can not preprare checkStatement")
                                return
                            }
                            checkStatement.setInt(1, id)
                            val resultCheck = checkStatement.executeQuery()
                            if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, kaiwa.lesson_id.toInt())
                                preparedStatement.setString(3, kaiwa.character)
                                preparedStatement.setString(4, kaiwa.kaiwa)
                                preparedStatement.setString(5, kaiwa.vi_mean)
                                preparedStatement.setString(6, kaiwa.c_roumaji)
                                preparedStatement.setString(7, kaiwa.j_roumaji)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }
                        preparedStatement.execute()
                        connection.close()
                    } else {
                        println("loi")
                        return
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
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getReference("$i")
            call.enqueue(object : Callback<List<Reference>> {
                override fun onResponse(p0: Call<List<Reference>>, p1: Response<List<Reference>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val referenceList = p1.body() as MutableList
                        for (j in referenceList) {
                            println(j.toString())
                        }
                        val sql =
                            "INSERT INTO reference" +
                                    " (id, lesson_id, japanese, roumaji, vietnamese, note)" +
                                    " VALUES (?, ?, ?, ?, ?, ?)"

                        val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                        if (preparedStatement == null) {
                            println("Connection is null. Cannot prepare preparedStatement.")
                            return
                        }
                        for (reference in referenceList) {
                            val id = reference.id.toInt()
                            val sqlCheck = "SELECT COUNT(*) FROM reference WHERE id = ?"

                            val checkStatement: PreparedStatement? = connection.prepareStatement(sqlCheck)
                            if (checkStatement == null) {
                                println("Can not preprare checkStatement")
                                return
                            }
                            checkStatement.setInt(1, id)
                            val resultCheck = checkStatement.executeQuery()
                            if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, reference.lesson_id.toInt())
                                preparedStatement.setString(3, reference.japanese)
                                preparedStatement.setString(4, reference.roumaji)
                                preparedStatement.setString(5, reference.vietnamese)
                                preparedStatement.setString(6, reference.note)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }
                        preparedStatement.execute()
                        connection.close()
                    } else {
                        println("loi")
                        return
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
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getMondai("$i")

            call.enqueue(object : Callback<List<Mondai>> {
                override fun onResponse(p0: Call<List<Mondai>>, p1: Response<List<Mondai>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val mondaiList = p1.body() as MutableList
                        for (j in mondaiList) {
                            println(j.toString())
                        }
                        val sql =
                            "INSERT INTO mondai" +
                                    " (id, lesson_id, namee, typee, question_num, answer)" +
                                    " VALUES (?, ?, ?, ?, ?, ?)"

                        val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                        if (preparedStatement == null) {
                            println("Connection is null. Cannot prepare preparedStatement.")
                            return
                        }
                        for (mondai in mondaiList) {
                            val id = mondai.id.toInt()
                            val sqlCheck = "SELECT COUNT(*) FROM mondai WHERE id = ?"

                            val checkStatement: PreparedStatement? = connection?.prepareStatement(sqlCheck)
                            if (checkStatement == null) {
                                println("Can not preprare checkStatement")
                                return
                            }
                            checkStatement.setInt(1, id)
                            val resultCheck = checkStatement.executeQuery()
                            if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, mondai.lesson_id.toInt())
                                preparedStatement.setString(3, mondai.name)
                                preparedStatement.setString(4, mondai.type)
                                preparedStatement.setString(5, mondai.question_num)
                                preparedStatement.setString(6, mondai.answer)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }
                        preparedStatement.execute()
                        connection.close()
                    } else {
                        println("loi")
                        return
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
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getBunkei("$i")

            call.enqueue(object : Callback<List<Bunkei>> {
                override fun onResponse(p0: Call<List<Bunkei>>, p1: Response<List<Bunkei>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val bunkeiList = p1.body() as MutableList
                        for (j in bunkeiList) {
                            println(j.toString())
                        }
                        val sql =
                            "INSERT INTO bunkei" +
                                    " (id, lesson_id, bunkei, vi_mean, roumaji, favorite)" +
                                    " VALUES (?, ?, ?, ?, ?, ?)"

                        val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                        if (preparedStatement == null) {
                            println("Connection is null. Cannot prepare preparedStatement.")
                            return
                        }
                        for (bunkei in bunkeiList) {
                            val id = bunkei.id.toInt()
                            val sqlCheck = "SELECT COUNT(*) FROM bunkei WHERE id = ?"

                            val checkStatement: PreparedStatement? = connection.prepareStatement(sqlCheck)
                            if (checkStatement == null) {
                                println("Can not preprare checkStatement")
                                return
                            }
                            checkStatement.setInt(1, id)
                            val resultCheck = checkStatement.executeQuery()
                            if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, bunkei.lesson_id.toInt())
                                preparedStatement.setString(3, bunkei.bunkei)
                                preparedStatement.setString(4, bunkei.vi_mean)
                                preparedStatement.setString(5, bunkei.roumaji)
                                preparedStatement.setString(6, bunkei.favorite)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }
                        preparedStatement.execute()
                        connection.close()
                    } else {
                        println("loi")
                        return
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
            val apiService = retrofitMazzi.getApiService()
            val call = apiService.getReibun("$i")

            call.enqueue(object : Callback<List<Reibun>> {
                override fun onResponse(p0: Call<List<Reibun>>, p1: Response<List<Reibun>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val reibunList = p1.body() as MutableList
                        for (j in reibunList) {
                            println(j.toString())
                        }
                        val sql =
                            "INSERT INTO reibun" +
                                    " (id, lesson_id, typee, reibun, vi_mean, j_roumaji)" +
                                    " VALUES (?, ?, ?, ?, ?, ?)"

                        val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                        if (preparedStatement == null) {
                            println("Connection is null. Cannot prepare preparedStatement.")
                            return
                        }
                        for (reibun in reibunList) {
                            val id = reibun.id.toInt()
                            val sqlCheck = "SELECT COUNT(*) FROM reibun WHERE id = ?"

                            val checkStatement: PreparedStatement? = connection.prepareStatement(sqlCheck)
                            if (checkStatement == null) {
                                println("Can not preprare checkStatement")
                                return
                            }
                            checkStatement.setInt(1, id)
                            val resultCheck = checkStatement.executeQuery()
                            if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                                preparedStatement.setInt(1, id)
                                preparedStatement.setInt(2, reibun.lesson_id.toInt())
                                preparedStatement.setString(3, reibun.type)
                                preparedStatement.setString(4, reibun.reibun)
                                preparedStatement.setString(5, reibun.vi_mean)
                                preparedStatement.setString(6, reibun.roumaji)

                                preparedStatement.addBatch()
                            }
                            checkStatement.close()
                        }
                        preparedStatement.execute()
                        connection.close()
                    } else {
                        println("loi")
                        return
                    }
                }

                override fun onFailure(p0: Call<List<Reibun>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            })
        }

    }
    fun getAlphabet() {
        val apiService = retrofitMazzi.getApiService()
        val call = apiService.getAlphabet()
        call.enqueue(object : Callback<List<Alphabet>> {
            override fun onResponse(p0: Call<List<Alphabet>>, p1: Response<List<Alphabet>>) {
                if (p1.isSuccessful && p1.body() != null) {
                    val connection = DatabaseHelper.connect()
                    val alphabetList = p1.body() as MutableList
                    for (j in alphabetList) {
                        println(j.toString())
                    }
                    val sql =
                        "INSERT INTO alphabet" +
                                " (id, romaji, hira, kata, groupe, example)" +
                                " VALUES (?, ?, ?, ?, ?, ?)"

                    val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                    if (preparedStatement == null) {
                        println("Connection is null. Cannot prepare preparedStatement.")
                        return
                    }
                    for (alphabet in alphabetList) {
                        val id = alphabet.id.toInt()
                        val sqlCheck = "SELECT COUNT(*) FROM alphabet WHERE id = ?"

                        val checkStatement: PreparedStatement? = connection.prepareStatement(sqlCheck)
                        if (checkStatement == null) {
                            println("Can not prepare checkStatement")
                            continue
                        }
                        checkStatement.setInt(1, id)
                        val resultCheck = checkStatement.executeQuery()
                        if (resultCheck.next() && resultCheck.getInt(1) == 0) {
                            preparedStatement.setInt(1, id)
                            preparedStatement.setString(2, alphabet.romaji)
                            preparedStatement.setString(3, alphabet.hira)
                            preparedStatement.setString(4, alphabet.kata)
                            preparedStatement.setString(5, alphabet.groupe)
                            preparedStatement.setString(6, alphabet.example)

                            preparedStatement.addBatch()
                        }
                        checkStatement.close()
                    }
                    preparedStatement.execute()
                    connection.close()
                } else {
                    println("loi")
                    return
                }
            }

            override fun onFailure(p0: Call<List<Alphabet>>, p1: Throwable) {
                p1.printStackTrace()
            }
        })
    }


    private fun getAudioFile(type: String) {
        for (i in 1..50) {
            val apiService = retrofitMazzi.getApiService()

            val call = apiService.getAudioFile(i.toString(), type) // Gọi phương thức
            call.enqueue(object : Callback<List<AudioFile>> {
                override fun onResponse(p0: Call<List<AudioFile>>, p1: Response<List<AudioFile>>) {
                    if (p1.isSuccessful && p1.body() != null) {
                        val connection = DatabaseHelper.connect()
                        val listAudioFile = p1.body() as MutableList<AudioFile>
                        for (audiofile in listAudioFile) {
                            println(audiofile)
                        }
                        try {
                            val sql =
                                "INSERT INTO audiofile (id, lesson_id, indx, typee, link, status) VALUES (?, ?, ?, ?, ?, ?)"
                            val preparedStatement: PreparedStatement? = connection?.prepareStatement(sql)
                            if (preparedStatement == null) {
                                println("Connection is null. Cannot prepare preparedStatement.")
                                return
                            }

                            for (audiofile in listAudioFile) {
                                println(audiofile)
                                val id = audiofile.id.toInt()

                                val checkSql = "SELECT COUNT(*) FROM audiofile WHERE id = ?"
                                val checkStatement: PreparedStatement? = connection.prepareStatement(checkSql)
                                if (checkStatement == null) {
                                    println("Connection is null. Cannot prepare statement.")
                                    return
                                }
                                checkStatement.setInt(1, id)
                                val resultSet = checkStatement.executeQuery()

                                if (resultSet.next() && resultSet.getInt(1) == 0) {
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
                            preparedStatement.executeBatch()
                            connection.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        println("Lỗi")
                        return
                    }
                }

                override fun onFailure(p0: Call<List<AudioFile>>, p1: Throwable) {
                    p1.printStackTrace()
                }
            })
        }
    }
}
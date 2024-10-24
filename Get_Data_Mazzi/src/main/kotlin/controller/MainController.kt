package controller


import controller.minna.*
import controller.minna.commom.AudioFileController
import kotlinx.coroutines.*
import request.retrofit.RetrofitAlphabet
import request.retrofit.RetrofitMinna
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainController {

    private val retrofitMinna = RetrofitMinna()
    private val retrofitAlphabet = RetrofitAlphabet()


    fun getAlphabet(): Unit = runBlocking {

        val schedule = Executors.newScheduledThreadPool(1)
        schedule.scheduleAtFixedRate({
            AlphabetController(retrofitAlphabet).getAlphabet()
        }, 0, 24, TimeUnit.HOURS)

    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getMinna(): Unit = runBlocking {

        val fileConfig = ConfigProperties()
        val delayMillis = fileConfig.readFile()
        val customDispatcher = newFixedThreadPoolContext(8, "CustomPool")


        while (true) {
            val tasks = listOf(

                launch(customDispatcher) { BunkeiController(retrofitMinna).getBunkei() },
                launch(customDispatcher) { GrammarController(retrofitMinna).getgrammar()},
                launch(customDispatcher) { KaiwaController(retrofitMinna).getkaiwa()},
                launch(customDispatcher) { MondaiController(retrofitMinna).getmondai()},
                launch(customDispatcher) { ReferenceController(retrofitMinna).getreference()},
                launch(customDispatcher) { ReibunController(retrofitMinna).getreibun()},
                launch(customDispatcher) { VocabularyController(retrofitMinna).getvocabulary()},
                launch(customDispatcher) { getFullAudioFile()},


            )
            tasks.forEach { it.join() }
            delay(delayMillis)
        }
    }

     private fun getFullAudioFile() {
        AudioFileController(retrofitMinna).getAudioFile("Kotoba")
        AudioFileController(retrofitMinna).getAudioFile("Kaiwa")
        AudioFileController(retrofitMinna).getAudioFile("Mondai")
        AudioFileController(retrofitMinna).getAudioFile("Bunkei")
        AudioFileController(retrofitMinna).getAudioFile("Reibun")
    }

}
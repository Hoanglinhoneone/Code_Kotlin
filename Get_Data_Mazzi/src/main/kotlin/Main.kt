import controller.ConfigProperties
import controller.MainController
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@OptIn(DelicateCoroutinesApi::class)
fun main(): Unit = runBlocking {

    val controller = MainController()
    val fileConfig = ConfigProperties()
    val delayMillis = fileConfig.readFile()
    val customDispatcher = newFixedThreadPoolContext(4, "CustomPool")


    val schedule = Executors.newScheduledThreadPool(1)
    schedule.scheduleAtFixedRate({
        controller.getAlphabet()
    }, 0, 24, TimeUnit.HOURS)

    while (true) {
        val tasks = listOf(
            launch(customDispatcher) {
                controller.getBunkei()
                controller.getGrammar()
            },
            launch(customDispatcher) {
                controller.getReibun()
                controller.getMondai()
            },

            launch(customDispatcher) {
                controller.getReibun()
                controller.getReference()
            },
            launch(customDispatcher) {
                controller.getKaiWa()
                controller.getVocabulary()
            },
        )
        tasks.forEach { it.join() }
        delay(delayMillis)
    }
}
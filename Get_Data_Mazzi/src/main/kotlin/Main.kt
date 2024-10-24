import controller.MainController
import kotlinx.coroutines.*

fun main(): Unit = runBlocking{
    val controller = MainController()
    controller.getAlphabet()
    controller.getMinna()
}
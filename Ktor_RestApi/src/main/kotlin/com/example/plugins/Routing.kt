package com.example.plugins

import com.example.model.DataChange
import com.example.model.TableChange
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
//        get("/") {
//            call.respondText("Hello World!")
//        }
//         Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
        get("/notifications") {
            val dataChange = DataChange.allTableChange()
            call.respond(dataChange)
        }

        post("/notifications") {
            try {
                val tableChange = call.receive<TableChange>()
                DataChange.addTableChange(tableChange)
                call.respond(HttpStatusCode.NoContent)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

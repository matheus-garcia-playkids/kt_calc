//Main.kt
import com.google.gson.Gson
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.header
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        routing {
            get("/api/ping/{count?}") {
                var count: Int = Integer.valueOf(call.parameters["count"]?: "1")
                if (count < 1) {
                    count = 1
                }
                var obj = Array<Entry>(count, {i -> Entry("$i: Hello, World! Como você está pedrào ?")})
                val gson = Gson()
                var str = gson.toJson(obj)
                call.response.header("Access-Control-Allow-Origin", "*")
                call.respondText(str, ContentType.Application.Json)
            };

            get("/api/multiplica/{a?}&{b?}") {

                var a: Int = Integer.valueOf(call.parameters["a"] ?: "0")
                var b: Int = Integer.valueOf(call.parameters["b"] ?: "0")

                var result = a * b

                call.response.header("Access-Control-Allow-Origin", "*")
                call.respondText(result.toString())

            }
        }
    }.start(wait = true)
}

data class Entry(val message: String)
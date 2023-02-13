package project.sites.public

import io.javalin.Javalin

fun main() {
    WebServer()
}

class WebServer {
    init {
        Javalin.create()
            .get("/") { ctx -> ctx.result("Hello Public World") }
            .start(5555)
    }
}
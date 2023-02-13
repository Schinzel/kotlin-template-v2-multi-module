package project.sites.admin

import io.javalin.Javalin

fun main() {
    WebServer()
}

class WebServer {
    init {
        Javalin.create()
            .get("/") { ctx -> ctx.result("Hello Admin World") }
            .start(5555)
    }
}
package project.sites.admin

import io.javalin.Javalin
import io.javalin.http.staticfiles.Location

fun main() {
    WebServer()
}

class WebServer {
    init {
        Javalin.create { config ->
            config.staticFiles.add("/site_admin", Location.CLASSPATH)
        }
            .get("/") { ctx -> ctx.result("Hello Admin World") }
            .start(5555)
    }
}
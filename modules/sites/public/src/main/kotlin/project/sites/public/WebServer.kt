package project.sites.public

import io.javalin.Javalin
import io.javalin.http.staticfiles.Location

fun main() {
    WebServer()
}

/**
 * http://127.0.0.1:5555/
 * http://127.0.0.1:5555/index.html
 */
class WebServer {
    init {
        Javalin.create { config ->
            config.staticFiles.add("/site_public", Location.CLASSPATH)
        }
            .get("/") { ctx -> ctx.result("Hello Public World") }
            .start(5555)
    }
}
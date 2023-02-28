package io.schinzel.sites.public

import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import io.schinzel.logic.db.MyDb

fun main() {
    PublicWebServer()
}

/**
 * http://127.0.0.1:5555/
 * http://127.0.0.1:5555/index.html
 */
class PublicWebServer {
    init {
        MyDb()
        Javalin.create { config ->
            config.staticFiles.add("/my_project/sites/public", Location.CLASSPATH)
        }
            .get("/") { ctx -> ctx.result("Hello Public World") }
            .start(5555)
    }
}
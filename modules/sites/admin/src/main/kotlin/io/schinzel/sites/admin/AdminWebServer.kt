package io.schinzel.sites.admin

import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import io.schinzel.logic.db.MyDb

fun main() {
    AdminWebServer()
}

class AdminWebServer {
    init {
        MyDb()
        Javalin.create { config ->
            config.staticFiles.add("/my_project/sites/admin", Location.CLASSPATH)
        }
            .get("/") { ctx -> ctx.result("Hello Admin World") }
            .start(5555)
    }
}
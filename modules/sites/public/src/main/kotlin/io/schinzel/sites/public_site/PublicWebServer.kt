package io.schinzel.sites.public_site

import io.javalin.Javalin
import io.javalin.core.security.RouteRole
import io.javalin.http.staticfiles.Location
import io.schinzel.logic.db.MyDb
import io.schinzel.sites.public_site.myapi1.MyApi
import se.refur.javalin.JavalinAnnotation
import se.refur.javalin.exposeClassEndpoints

fun main() {
    PublicWebServer()
}

// Available roles
enum class MyAccessRoles : RouteRole {
    PUBLIC, ADMIN
}

/**
 * http://127.0.0.1:5555/
 * http://127.0.0.1:5555/index.html
 * java -jar myJar.jar
 */
class PublicWebServer {
    init {
        MyDb()
        JsClientGen.generate()

        // Setup roles for endpoints
        JavalinAnnotation.setRoles(MyAccessRoles.values().associateBy { it.name })

//        Javalin.create { config ->
//            config.staticFiles.add("/my_project/sites/public", Location.CLASSPATH)
//        }
//            .get("/") { ctx -> ctx.result("Hello Public World") }
//            .start(5555)
//            // expose endpoints via class
//            .exposeClassEndpoints(MyApi::class)

        Javalin.create {config ->
            config.addStaticFiles("/my_project/sites/public", Location.CLASSPATH)
            config.accessManager { handler, ctx, _ -> handler.handle(ctx) }
        }
            .get("/") { ctx -> ctx.result("Hello Public World") }
            .exposeClassEndpoints(MyApi::class)
            .start(5555)
    }
}
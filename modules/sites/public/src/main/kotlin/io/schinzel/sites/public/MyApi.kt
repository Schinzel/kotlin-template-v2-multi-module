package io.schinzel.sites.public

import io.javalin.http.HandlerType
import se.refur.javalin.Api

class MyApi {
    @Api(type = HandlerType.GET, path = "/api/v1/myEndpoint", accessRole = "PUBLIC")
    fun myEndpoint(): String {
        return "Hello World"
    }
}
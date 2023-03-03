package io.schinzel.sites.public_site.myapi1

import com.google.common.annotations.VisibleForTesting
import io.javalin.http.HandlerType
import se.refur.javalin.Api

@VisibleForTesting
class MyApi1 {

    @VisibleForTesting
    @Api(type = HandlerType.GET, path = "/api/v1/myEndpoint", accessRole = "PUBLIC")
    fun myEndpoint(): String {
        return "Hello World"
    }

}
package io.schinzel.sites.public_site.myapi1

import com.google.common.annotations.VisibleForTesting
import io.javalin.http.HandlerType
import io.schinzel.basicutils.RandomUtil
import se.refur.javalin.Api

class MyApi1 {

    @Api(type = HandlerType.POST, path = "/api/v1/myEndpoint", accessRole = "PUBLIC")
    fun myEndpoint(): String {
        return "Hello World " + RandomUtil.getRandomString(5)
    }

    @Api(type = HandlerType.POST, path = "myOtherEndpoint", accessRole = "PUBLIC")
    fun myOtherEndpoint(): String {
        return "Hello World " + RandomUtil.getRandomString(5)
    }


}
package io.schinzel.sites.public_site.myapi2

import io.javalin.http.HandlerType
import io.schinzel.basicutils.RandomUtil
import se.refur.javalin.Api
import se.refur.javalin.Param
import se.refur.javalin.ParameterType

class MyApi2 {
    @Api(type = HandlerType.POST, path = "/api/second", accessRole = "ADMIN")
    fun apiQueryEndpoint(
        @Param("name", ParameterType.QUERY) userName: String,
        @Param("age", ParameterType.QUERY) userAge: Int,
        @Param("something", ParameterType.QUERY) requestDate: Long
    ): String = "success " + RandomUtil.getRandomString(5)

}
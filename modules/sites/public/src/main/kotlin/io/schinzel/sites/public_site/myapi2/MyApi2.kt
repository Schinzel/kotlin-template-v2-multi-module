package io.schinzel.sites.public_site.myapi2

import io.javalin.http.HandlerType
import io.schinzel.basicutils.RandomUtil
import se.refur.javalin.Api
import se.refur.javalin.Param
import se.refur.javalin.ParameterType
import java.time.LocalDate

class MyApi2 {
    @Api(type = HandlerType.GET, path = "/api/second", accessRole = "ADMIN")
    fun apiQueryEndpoint(
        @Param("name", ParameterType.QUERY) userName: String,
        @Param("age", ParameterType.QUERY) userAge: Int,
        @Param("date", ParameterType.QUERY) requestDate: LocalDate
    ): String = "success " + RandomUtil.getRandomString(5)

}
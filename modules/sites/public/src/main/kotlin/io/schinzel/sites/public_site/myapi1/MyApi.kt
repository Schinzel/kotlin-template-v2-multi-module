package io.schinzel.sites.public_site.myapi1

import io.javalin.http.HandlerType
import io.schinzel.basicutils.RandomUtil
import se.refur.javalin.Api
import se.refur.javalin.Param
import se.refur.javalin.ParameterType

class MyApi {

    @Api(
        type = HandlerType.POST, path = "/api/v1/myEndpoint", accessRole = "PUBLIC",
        description = "My first endpoint"
    )
    fun myEndpoint(): String {
        return "Hello World " + RandomUtil.getRandomString(5)
    }


    @Api(
        type = HandlerType.POST, path = "/api/v1/doubleIt", accessRole = "PUBLIC",
        description = "Double a number"
    )
    fun doubleIt(@Param("anyNumber", ParameterType.FORM, "Any number") anyNumber: Int): Int {
        return anyNumber * 2
    }


    @Api(
        type = HandlerType.POST, path = "/api/findGoodMatch", accessRole = "PUBLIC",
        description = "Find a good match for a man"
    )
    fun findGoodMatch(
        @Param("firstName", ParameterType.FORM, description = "The first name of the person to find a match for")
        firstName: String,
        @Param("lastName", ParameterType.FORM, description = "The last name of the person to find a match for")
        lastName: String,
        @Param("age", ParameterType.FORM, description = "The age of the person to find a match for")
        age: Int
    ): PersonDto {
        return PersonDto(
            firstName = "Kristina",
            secondName = "Schinzel",
            age = 46
        )
    }

}

data class PersonDto(
    val firstName: String,
    val secondName: String,
    val age: Int,
)
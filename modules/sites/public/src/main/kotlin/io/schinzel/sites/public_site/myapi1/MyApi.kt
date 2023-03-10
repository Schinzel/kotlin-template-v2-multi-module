package io.schinzel.sites.public_site.myapi1

import io.javalin.http.HandlerType
import io.schinzel.basic_utils_kotlin.printlnWithPrefix
import io.schinzel.basicutils.RandomUtil
import se.refur.javalin.Api
import se.refur.javalin.Param
import se.refur.javalin.ParameterType

class MyApi {

    @Api(type = HandlerType.POST, path = "/api/v1/myEndpoint", accessRole = "PUBLIC")
    fun myEndpoint(): String {
        return "Hello World " + RandomUtil.getRandomString(5)
    }


    @Api(type = HandlerType.POST, path = "/api/v1/doubleIt", accessRole = "PUBLIC")
    fun doubleIt(@Param("anyNumber", ParameterType.FORM) anyNumber: Int): Int {
        return anyNumber * 2
    }


    @Api(type = HandlerType.POST, path = "/api/getWife", accessRole = "PUBLIC")
    fun getWife(
        @Param("firstName", ParameterType.FORM) firstName: String,
        @Param("lastName", ParameterType.FORM) lastName: String,
        @Param("age", ParameterType.FORM) age: Int
    ): PersonDto {
        firstName.printlnWithPrefix("firstName")
        age.printlnWithPrefix("age")
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
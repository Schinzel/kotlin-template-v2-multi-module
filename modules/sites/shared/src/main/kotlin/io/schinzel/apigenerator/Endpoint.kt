package io.schinzel.apigenerator

import io.javalin.http.HandlerType
import se.refur.javalin.Api
import se.refur.javalin.Param
import java.lang.reflect.Method

class Endpoint(method: Method) {
    private val apiAnnotation: Api = method.getAnnotation(Api::class.java)
    val path: String = apiAnnotation.path
    val requestType: HandlerType = apiAnnotation.type
    val parameters: List<EndpointParameter> = method.parameters
        .filter { it.isAnnotationPresent(Param::class.java) }
        .map { EndpointParameter(it) }
    val returnDataTypeName: String = method.returnType.simpleName
    val returnDataType: Class<*> = method.returnType
    val description: String = apiAnnotation.description


    override fun toString(): String {
        return "path = $path, requestType = $requestType, returnDataType = $returnDataTypeName"
    }
}
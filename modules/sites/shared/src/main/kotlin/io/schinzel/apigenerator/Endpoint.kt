package io.schinzel.apigenerator

import io.javalin.http.HandlerType
import se.refur.javalin.Api
import se.refur.javalin.Param
import java.lang.reflect.Method

class Endpoint(method: Method) {
    private val annotation = method.getAnnotation(Api::class.java)
    val path: String = annotation.path
    val requestType: HandlerType = annotation.type
    val parameters: List<EndpointParameter> = method.parameters
        .filter { it.isAnnotationPresent(Param::class.java) }
        .map { EndpointParameter(it) }
    val returnDataType = method.returnType.simpleName


    override fun toString(): String {
        return "path = $path, requestType = $requestType, returnDataType = $returnDataType"
    }
}
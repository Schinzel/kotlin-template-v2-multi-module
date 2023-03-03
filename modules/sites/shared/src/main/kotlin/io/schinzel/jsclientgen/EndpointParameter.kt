package io.schinzel.jsclientgen

import io.schinzel.basicutils.thrower.Thrower
import se.refur.javalin.Param
import java.lang.reflect.Parameter

class EndpointParameter(parameter: Parameter) {
    private val annotation = parameter.getAnnotation(Param::class.java)
    val name = annotation.paramName
    val type = annotation.parameterType
    val dataType = parameter.type.simpleName

    init {
        Thrower.throwIfFalse(parameter.isAnnotationPresent(Param::class.java))
            .message("Parameter is not annotated with @Param")
    }

    override fun toString(): String {
        return "name = $name, type = $type, dataType = $dataType"
    }

}
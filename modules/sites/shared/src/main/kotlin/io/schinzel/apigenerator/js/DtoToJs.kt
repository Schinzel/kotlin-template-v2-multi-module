package io.schinzel.apigenerator.js

import io.schinzel.jstranspiler.transpiler.IToJavaScript
import io.schinzel.jstranspiler.transpiler.KotlinClass
import io.schinzel.jstranspiler.transpiler.KotlinEnum

/**
 * Converts a data object to a JavaScript class.
 */
class DtoToJs(dto: Class<*>) {

    val javaScript: String

    init {
        val transpiler: IToJavaScript = when (dto.isEnum) {
            true -> KotlinEnum(dto)
            false -> KotlinClass(dto)
        }
        javaScript =  transpiler.toJavaScript()
    }
}
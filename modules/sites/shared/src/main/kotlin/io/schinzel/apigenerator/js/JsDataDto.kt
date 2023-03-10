package io.schinzel.apigenerator.js

import io.schinzel.apigenerator.Endpoint
import io.schinzel.jstranspiler.transpiler.IToJavaScript
import io.schinzel.jstranspiler.transpiler.KotlinClass
import io.schinzel.jstranspiler.transpiler.KotlinEnum

class JsDataDto(endpoint: Endpoint) {

    val javasScript: String

    init {
        val sb = StringBuilder()
        if (JsDataTypeMapper.isDto(endpoint.returnDataTypeName)) {
            val js = getJsFromDto(endpoint.returnDataType)
            sb.append(js)
        }
        javasScript = sb.toString()
    }

    companion object {

        fun getJsFromDto(dto: Class<*>): String {
            val transpiler: IToJavaScript = when (dto.isEnum) {
                true -> KotlinEnum(dto)
                false -> KotlinClass(dto)
            }
            return transpiler.toJavaScript()
        }
    }
}
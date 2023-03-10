package io.schinzel.apigenerator.js

import io.schinzel.apigenerator.Endpoint
import io.schinzel.jstranspiler.transpiler.IToJavaScript
import io.schinzel.jstranspiler.transpiler.KotlinClass
import io.schinzel.jstranspiler.transpiler.KotlinEnum


class JsDtoGenerator(endpoints: List<Endpoint>) {
    val javaScript: String

    init {
        javaScript = endpoints.joinToString("") { endpoint ->
            val dto = endpoint.returnDataType
            when (JsDataTypeMapper.isDto(dto.simpleName)) {
                true -> dtoAsJavaScript(dto)
                false -> ""
            }
        }
    }

    companion object {

        private fun dtoAsJavaScript(dto: Class<*>): String {
            val transpiler: IToJavaScript = when (dto.isEnum) {
                true -> KotlinEnum(dto)
                false -> KotlinClass(dto)
            }
            return transpiler.toJavaScript()
        }
    }
}
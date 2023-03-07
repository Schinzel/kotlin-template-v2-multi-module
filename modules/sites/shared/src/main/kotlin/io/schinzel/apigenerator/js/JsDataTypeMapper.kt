package io.schinzel.apigenerator.js

object JsDataTypeMapper {
    fun getJsDataType(kotlinDataType: String): String {
        if (kotlinDataType.endsWith("dto", true)){
            return kotlinDataType
        }
        return when (kotlinDataType) {
            "String" -> "string"
            "int" -> "number"
            "long" -> "number"
            "double" -> "number"
            "boolean" -> "boolean"
            else -> throw IllegalArgumentException("Unknown data type $kotlinDataType")
        }
    }

    fun isDto(kotlinDataType: String): Boolean {
        return kotlinDataType.endsWith("dto", true)
    }
}
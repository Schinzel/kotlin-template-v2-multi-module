package io.schinzel.apigenerator.js

object JsDataTypeMapper {
    fun getJsDataType(kotlinDataType: String): String {
        return when (kotlinDataType) {
            "String" -> "string"
            "Int" -> "number"
            "Long" -> "number"
            "Double" -> "number"
            "Boolean" -> "boolean"
            else -> throw IllegalArgumentException("Unknown data type $kotlinDataType")
        }
    }
}
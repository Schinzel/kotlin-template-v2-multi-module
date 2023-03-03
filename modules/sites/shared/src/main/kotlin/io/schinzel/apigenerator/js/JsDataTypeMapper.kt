package io.schinzel.apigenerator.js

object JsDataTypeMapper {
    fun getJsDataType(kotlinDataType: String): String {
        return when (kotlinDataType) {
            "String" -> "string"
            "int" -> "number"
            "long" -> "number"
            "double" -> "number"
            "boolean" -> "boolean"
            else -> throw IllegalArgumentException("Unknown data type $kotlinDataType")
        }
    }
}